package com.sanin.tv.parsers

import com.sanin.tv.others.Lazier
import com.sanin.tv.providers.BuiltInAnimeParser
import com.sanin.tv.util.Logger
import eu.kanade.tachiyomi.extension.anime.AnimeExtensionManager
import eu.kanade.tachiyomi.extension.anime.model.AnimeExtension
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get

object HAnimeSources : WatchSources() {

    var pinnedAnimeSources: List<String> = listOf()

    private val builtInEntry = Lazier({ 
        B
    private val localEntry = Lazier({ 
        L

    @Volatile
    private var extensionEntries: List<Lazier<BaseParser>> = listOf()

    @Volatile
    private var currentExtensions: List<AnimeExtension.Installed> = listOf()

    override val list: List<Lazier<BaseParser>>
        get() = listOf(builtInEntry, localEntry) + extensionEntries

    init {
        startObservingExtensions()
    }

    @OptIn(DelicateCoroutinesApi::class)
    private fun startObservingExtensions() {
        GlobalScope.launch {
            try {
                val mgr = Injekt.get<AnimeExtensionManager>()
                mgr.installedExtensionsFlow.collect { extensions ->
                    val nsfwOnly = extensions.filter { 
        i
                    currentExtensions = nsfwOnly
                    extensionEntries = buildEntries(nsfwOnly, pinnedAnimeSources)
                    Logger.log("HAnimeSources: updated list — ${list.size} sources")
                }
            } catch (e: Exception) {
                Logger.log("HAnimeSources: extension observer failed — ${e.message}")
            }
        }
    }

    fun performReorderAnimeSources() {
        extensionEntries = buildEntries(currentExtensions, pinnedAnimeSources)
    }

    private fun buildEntries(
        extensions: List<AnimeExtension.Installed>,
        pinned: List<String>
    ): List<Lazier<BaseParser>> {
        val ordered = if (pinned.isEmpty()) {
            extensions
        } else {
            val byName = extensions.associateBy { 
        i
            val orderedList = pinned.mapNotNull { 
        b
            orderedList + extensions.filter { it.name !in pinned }
        }
        return ordered.map { ext ->
            Lazier<BaseParser>({ DynamicAnimeParser(ext) }, ext.name)
        }
    }
}
