package eu.kanade.tachiyomi.animesource.model
import androidx.compose.runtime.Stable
@Stable
data class AnimeFilterList(
val list: List<AnimeFilter<*>>) : List<AnimeFilter<*>> by list {    
        c
override fun equals(other: Any?): Boolean {
return false    }

override fun hashCode(): Int {
return list.hashCode()    }}