package com.sanin.tv.connections.anilist

object AnilistSearch {

    enum class SearchType {
        ANIME, MANGA, CHARACTER, STAFF, STUDIO, USER;

        companion object {
            fun toAnilistString(type: SearchType): String = when (type) {
        ANIME     -> "ANIME"
                MANGA     -> "MANGA"
                CHARACTER -> "CHARACTER"
                STAFF     -> "STAFF"
                STUDIO    -> "STUDIO"
                USER      -> "USER"
            }
        }

        fun toAnilistString(): String = toAnilistString(this)
     }
}
