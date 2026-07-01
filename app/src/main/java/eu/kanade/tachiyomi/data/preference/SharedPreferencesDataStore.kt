package eu.kanade.tachiyomi.data.preference
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceDataStore
class SharedPreferencesDataStore(
private val prefs: SharedPreferences) : PreferenceDataStore() {
    override fun getBoolean(key: String?, defValue: Boolean): Boolean {
return prefs.getBoolean(key, defValue)
     }
override fun putBoolean(key: String?, value: Boolean) {        
        p
}

override fun getInt(key: String?, defValue: Int): Int {
return prefs.getInt(key, defValue)
     }
override fun putInt(key: String?, value: Int) {        
        p
}

override fun getLong(key: String?, defValue: Long): Long {
return prefs.getLong(key, defValue)
     }
override fun putLong(key: String?, value: Long) {        
        p
}

override fun getFloat(key: String?, defValue: Float): Float {
return prefs.getFloat(key, defValue)
     }
override fun putFloat(key: String?, value: Float) {        
        p
}

override fun getString(key: String?, defValue: String?): String? {
return prefs.getString(key, defValue)
     }
override fun putString(key: String?, value: String?) {        
        p
}

override fun getStringSet(key: String?, defValues: MutableSet<String>?): MutableSet<String>? {
return prefs.getStringSet(key, defValues)
     }
override fun putStringSet(key: String?, values: MutableSet<String>?) {        
        p
}
}