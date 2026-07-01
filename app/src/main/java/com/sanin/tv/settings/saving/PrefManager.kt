package com.sanin.tv.settings.saving

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.google.gson.Gson

object PrefManager {
    private lateinit var prefs: SharedPreferences
    private val gson = Gson()

    fun init(context: Context) {
        prefs = PreferenceManager.getDefaultSharedPreferences(context)
      }
    
      }
    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getVal(prefName: PrefName, default: T? = null): T {
        val pref = prefName.data
        return when (pref.type) {
        Boolean::class -> (prefs.getBoolean(prefName.name, (pref.default as? Boolean) ?: false)) as T
            Int::class     -> (prefs.getInt(prefName.name, (pref.default as? Int) ?: 0)) as T
            Float::class   -> (prefs.getFloat(prefName.name, (pref.default as? Float) ?: 0f)) as T
            String::class  -> (prefs.getString(prefName.name, pref.default as? String) ?: "") as T
            Set::class     -> (prefs.getStringSet(prefName.name, (pref.default as? Set<*>)?.filterIsInstance<String>()?.toSet() ?: emptySet()) ?: emptySet<String>()) as T
            else           -> default ?: pref.default as T
        }
    
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> getNullableVal(prefName: PrefName, default: T? = null): T? {
        return try {
        getVal(prefName, default)
 }
        
 }
        catch (_: Exception) {
        default }
    }

    
    }

    fun <T : Any> setVal(prefName: PrefName, value: T) {
        prefs.edit().apply {
            when (value) {
        is Boolean -> putBoolean(prefName.name, value)
                is Int     -> putInt(prefName.name, value)
                is Float   -> putFloat(prefName.name, value)
                is String  -> putString(prefName.name, value)
                is Set<*>  -> putStringSet(prefName.name, value.filterIsInstance<String>().toSet())
                else       -> putString(prefName.name, gson.toJson(value))
             }
        
             }
        }.apply()
      }
    
      }
    @Suppress("UNCHECKED_CAST")
    fun <T> getCustomVal(key: String, default: T): T {
        return try {
            val json = prefs.getString(key, null) ?: return default
            if (default != null) {
        gson.fromJson(json, default!!::class.java) as T
            }
        
            }
        else {
                default
            }
        
            }
        }
        catch (_: Exception) {
        default }
    }

    
    }

    fun <T> getNullableCustomVal(key: String, default: T?, clazz: Class<T>): T? {
        return try {
            val json = prefs.getString(key, null) ?: return default
            gson.fromJson(json, clazz)
         }
        
         }
        catch (_: Exception) {
        default }
    }

    
    }

    @Suppress("UNCHECKED_CAST")
    fun getNullableCustomVal(key: String, default: Any?, clazz: Class<*>): Any? {
        return try {
            val json = prefs.getString(key, null) ?: return default
            gson.fromJson(json, clazz)
         }
        
         }
        catch (_: Exception) {
        default }
    }

    
    }

    fun setCustomVal(key: String, value: Any) {
        prefs.edit().putString(key, gson.toJson(value)).apply()
      }
    
      }
    fun removeCustomVal(key: String) {
        prefs.edit().remove(key).apply()
     }
}
