package com.sanin.tv.media.user
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sanin.tv.connections.anilist.Anilist
import com.sanin.tv.connections.mal.MAL
import com.sanin.tv.media.Media
import com.sanin.tv.settings.saving.PrefManager
import com.sanin.tv.settings.saving.PrefName
import com.sanin.tv.tryWithSuspend
class ListViewModel : ViewModel() {
    var grid = MutableLiveData(PrefManager.getVal<Boolean>(PrefName.ListGrid))    
private val lists = MutableLiveData<MutableMap<String, ArrayList<Media>>>()    
private val unfilteredLists = MutableLiveData<MutableMap<String, ArrayList<Media>>>()    
fun getLists(): LiveData<MutableMap<String, ArrayList<Media>>> = lists    suspend 
fun loadLists(anime: Boolean, userId: Int, sortOrder: String? = null) {
    val rescueMode: Boolean = PrefManager.getVal(PrefName.RescueMode)
if (rescueMode) {
        loadListsFromMAL(anime)
return        }
tryWithSuspend {
    val res = Anilist.query.getMediaLists(anime, userId, sortOrder)
        lists.postValue(res)
            unfilteredLists.postValue(res)
         }
    }

private suspend 
fun loadListsFromMAL(anime: Boolean) {        
        t
    val statuses = if (anime)
        listOf("watching" to "Watching", "completed" to "Completed", "plan_to_watch" to "Planned",
                    "on_hold" to "Paused", "dropped" to "Dropped")
else                listOf("reading" to "Reading", "completed" to "Completed", "plan_to_read" to "Planned",                    "on_hold" to "Paused", "dropped" to "Dropped")            
val result = mutableMapOf<String, ArrayList<Media>>()
        lists.postValue(filteredLists)
      }
fun searchLists(search: String) {
if (search.isEmpty()) {
        lists.postValue(unfilteredLists.value)
return        }

val currentLists = unfilteredLists.value ?: return
val filteredLists = currentLists.mapValues { 
        e
    }

fun unfilterLists() {        
        l
}
