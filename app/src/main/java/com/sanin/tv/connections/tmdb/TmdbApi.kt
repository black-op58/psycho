package com.sanin.tv.connections.tmdb

import android.content.Context
import android.net.Uri
import androidx.documentfile.provider.DocumentFile
import com.sanin.tv.App
import com.sanin.tv.Mapper
import com.sanin.tv.client
import com.sanin.tv.settings.saving.PrefManager
import com.sanin.tv.settings.saving.PrefName
import com.sanin.tv.util.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.*
import java.io.File

/**
 * Fetches anime logo art from ani.zip, which provides clearlogo PNGs
 * sourced from TheTVDB. No API key required.
 *
 * Cache hierarchy (fastest → slowest):
 *  1. In-memory HashMap           — instant, lost on process death
 *  2. Disk JSON file              — survives restarts
 *  3. Network (ani.zip API)       — only for entries not yet in cache
 */
object TmdbApi {
    private const val ANIZIP_API      = "https://api.ani.zip/mappings?anilist_id="
    private const val ANIZIP_TIMEOUT  = 5_000L
    private const val CACHE_DIR       = "tmdb"
    private const val CACHE_FILE      = "logos.json"
    private const val IMAGE_PROXY     = "https://wsrv.nl/?url="

    private val cache = HashMap<Int, String>()
    private var diskLoaded = false

    // ── Storage helpers ──────────────────────────────────

    private fun getInternalFile(): File {
        val ctx = App.context ?: return File(CACHE_FILE)
        return File(ctx.getDir(CACHE_DIR, Context.MODE_PRIVATE), CACHE_FILE)
    }

    private fun getSafCacheFile(): DocumentFile? {
        val ctx = App.context ?: return null
        val uriStr = PrefManager.getVal<String>(PrefName.CacheStorageUri)
        if (uriStr.isBlank()) return null
        val tree = DocumentFile.fromTreeUri(ctx, Uri.parse(uriStr)) ?: return null
        return tree.findFile(CACHE_FILE)
    }

    private fun getSafCacheTree(): DocumentFile? {
        val ctx = App.context ?: return null
        val uriStr = PrefManager.getVal<String>(PrefName.CacheStorageUri)
        if (uriStr.isBlank()) return null
        return DocumentFile.fromTreeUri(ctx, Uri.parse(uriStr))
    }

    // ── Disk I/O ─────────────────────────────────────────

    private fun readDiskRaw(): String? {
        val ctx = App.context ?: return null
        val safFile = getSafCacheFile()
        return if (safFile != null) {
            ctx.contentResolver.openInputStream(safFile.uri)?.use { it.readText() }
        } else {
            val f = getInternalFile()
            if (f.exists()) f.readText() else null
        }
    }

    private fun writeDiskRaw(json: String) {
        val ctx = App.context ?: return
        val tree = getSafCacheTree()
        if (tree != null) {
            val safFile = tree.findFile(CACHE_FILE)
                ?: tree.createFile("application/json", CACHE_FILE) ?: return
            ctx.contentResolver.openOutputStream(safFile.uri, "w")?.use { it.write(json.toByteArray()) }
        } else {
            val f = getInternalFile()
            f.parentFile?.mkdirs()
            f.writeText(json)
        }
    }

    @Synchronized
    private fun loadFromDisk() {
        if (diskLoaded) return
        diskLoaded = true
        try {
            val raw = readDiskRaw() ?: return
            val map = Mapper.json.decodeFromString<Map<String, String>>(raw)
            map.forEach { (k, v) -> cache[k.toInt()] = v }
            Logger.log("TmdbApi: loaded ${map.size} cache entries")
        } catch (e: Exception) {
            Logger.log("TmdbApi: disk load failed — ${e.message}")
        }
    }

    @Synchronized
    private fun saveToDisk() {
        try {
            writeDiskRaw(Mapper.json.encodeToString(cache.mapKeys { it.key.toString() }))
        } catch (e: Exception) {
            Logger.log("TmdbApi: disk save failed — ${e.message}")
        }
    }

    // ── Storage migration ────────────────────────────────

    fun migrateStorageSaf(newTreeUri: Uri) {
        val ctx = App.context ?: return
        try {
            val json = readDiskRaw()
            val newTree = DocumentFile.fromTreeUri(ctx, newTreeUri)
            val newFile = newTree?.findFile(CACHE_FILE)
                ?: newTree?.createFile("application/json", CACHE_FILE)
            if (newFile != null && json != null) {
                ctx.contentResolver.openOutputStream(newFile.uri, "w")
                    ?.use { it.write(json.toByteArray()) }
            }
            getSafCacheFile()?.delete() ?: getInternalFile().delete()
            diskLoaded = false
        } catch (e: Exception) {
            Logger.log("TmdbApi: SAF migration failed — ${e.message}")
        }
    }

    fun resetStorageToInternal() {
        val ctx = App.context ?: return
        try {
            val json = readDiskRaw()
            getSafCacheFile()?.delete()
            if (json != null) {
                val f = getInternalFile()
                f.parentFile?.mkdirs()
                f.writeText(json)
            }
            diskLoaded = false
        } catch (e: Exception) {
            Logger.log("TmdbApi: reset to internal failed — ${e.message}")
        }
    }

    // ── Public helpers ────────────────────────────────────

    fun cacheFileSizeBytes(): Long =
        getSafCacheFile()?.length() ?: getInternalFile().length()

    fun cacheFilePath(): String? =
        getSafCacheFile()?.uri?.lastPathSegment ?: getInternalFile().absolutePath

    // ── Logo extraction from ani.zip response ─────────────

    private fun extractLogoUrl(element: JsonElement): String? {
        return when (element) {
            is JsonObject -> {
                val logoKeys = listOf("clearlogo", "clearLogo", "logo", "logoImage")
                val direct = logoKeys.firstNotNullOfOrNull { key ->
                    (element[key] as? JsonPrimitive)?.contentOrNull
                        ?.takeIf { isUrl(it) }
                }
                if (direct != null) return direct

                val imagesArray = element["images"] as? JsonArray
                imagesArray?.filterIsInstance<JsonObject>()
                    ?.firstOrNull { img ->
                        (img["coverType"] as? JsonPrimitive)?.contentOrNull
                            ?.equals("Clearlogo", ignoreCase = true) == true
                    }
                    ?.let { (it["url"] as? JsonPrimitive)?.contentOrNull?.takeIf { u -> isUrl(u) } }
            }
            is JsonArray -> {
                element.filterIsInstance<JsonObject>().firstNotNullOfOrNull { extractLogoUrl(it) }
            }
            else -> null
        }
    }

    private fun isUrl(value: String): Boolean =
        value.startsWith("http://") || value.startsWith("https://")

    // ── Public API ────────────────────────────────────────

    suspend fun getLogoUrl(anilistId: Int, isAnime: Boolean): String? {
        if (!isAnime) return null

        if (!diskLoaded) withContext(Dispatchers.IO) { loadFromDisk() }
        cache[anilistId]?.let { return it.ifEmpty { null } }

        return withContext(Dispatchers.IO) {
            try {
                val url = withTimeout(ANIZIP_TIMEOUT) {
                    val response = client.get("$ANIZIP_API$anilistId")
                    val jsonElement = Mapper.json.parseToJsonElement(response.text)
                    extractLogoUrl(jsonElement)
                }

                val logoUrl = if (url != null) "$IMAGE_PROXY$url" else ""

                cache[anilistId] = logoUrl
                saveToDisk()

                Logger.log("TmdbApi: anilist=$anilistId → ${logoUrl.ifEmpty { "no logo" }}")
                logoUrl.ifEmpty { null }

            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Logger.log("TmdbApi: fallback anilist=$anilistId → ${e.message}")
                null
            }
        }
    }

    fun clearCache() {
        cache.clear()
        diskLoaded = false
        try {
            getSafCacheFile()?.delete()
            getInternalFile().delete()
            Logger.log("TmdbApi: cache cleared")
        } catch (e: Exception) {
            Logger.log("TmdbApi: clearCache error — ${e.message}")
        }
    }
}
