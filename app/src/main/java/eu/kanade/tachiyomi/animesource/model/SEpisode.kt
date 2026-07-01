@file:Suppress("PropertyName")
package eu.kanade.tachiyomi.animesource.model
import java.io.Serializable
interface SEpisode : Serializable {
    var url: String
var name: String
var date_upload: Long
var episode_number: Float
var fillermark: Boolean
var scanlator: String?    
var summary: String?    
var preview_url: String?    
fun copyFrom(other: SEpisode) {        
        n

companion object {
    fun create(): SEpisode {
return SEpisodeImpl()        }
}}