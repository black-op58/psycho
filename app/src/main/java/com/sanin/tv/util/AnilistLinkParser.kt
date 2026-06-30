package com.sanin.tv.util

object AnilistLinkParser {
    /**
     * Parse an AniList link to extract the media ID and type.
     * Supports formats:
     * - https://anilist.co/anime/12345
     * - https://anilist.co/manga/12345
     * - anilist.co/anime/12345/Title
     * - 12345 (raw ID)
     */
    fun parse(link: String): AnilistLinkResult? {
        val trimmed = link.trim()
        
        // Try raw numeric ID first
        trimmed.toIntOrNull()?.let { return AnilistLinkResult(it, "ANIME") }

        // Try parsing URL
        try {
            val url = java.net.URI(trimmed)
            val path = url.path?.trim('/') ?: return null
            val parts = path.split("/")
            if (parts.size >= 2) {
                val type = parts[0].uppercase()
                val id = parts[1].toIntOrNull()
                if (id != null && (type == "ANIME" || type == "MANGA")) {
                    return AnilistLinkResult(id, type)
                }
            }
        } catch (_: Exception) {}

        return null
    }
}

data class AnilistLinkResult(val id: Int, val type: String)
