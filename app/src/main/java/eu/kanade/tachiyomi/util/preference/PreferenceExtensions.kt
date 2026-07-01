package eu.kanade.tachiyomi.util.preference
import android.widget.CompoundButton
import eu.kanade.core.preference.PreferenceMutableState
import kotlinx.coroutines.CoroutineScope
import tachiyomi.core.preference.Preference/** * Binds a checkbox or switch view with a boolean preference. */
fun CompoundButton.bindToPreference(pref: Preference<Boolean>) {    
        i
fun <T> Preference<Set<T>>.plusAssign(item: T) {    
        s
fun <T> Preference<Set<T>>.minusAssign(item: T) {    
        s

fun Preference<Boolean>.toggle(): Boolean {    
        s
return get()
 }
fun <T> Preference<T>.asState(presenterScope: CoroutineScope): PreferenceMutableState<T> {
return PreferenceMutableState(this, presenterScope)
}