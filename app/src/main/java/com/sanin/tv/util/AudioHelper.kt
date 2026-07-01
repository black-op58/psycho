package com.sanin.tv.util
import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
class AudioHelper(
private val context: Context) {
    private val audioManager: AudioManager =        context.getSystemService(Context.AUDIO_SERVICE) as AudioManager    
private var mediaPlayer: MediaPlayer? = null
fun routeAudioToSpeaker() {        
        a

private val maxVolume: Int        get() = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)    
private var oldVolume: Int = 0
fun setVolume(percentage: Int) {        
        o
val volume = (maxVolume * percentage) / 100        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)
     }
fun playAudio(audio: Int) {        
        m
        mediaPlayer?.setOnCompletionListener {
            setVolume(oldVolume)
        audioManager.abandonAudioFocus(null)
            it.release()
         }
mediaPlayer?.setOnPreparedListener {
        it.start()
}
}

fun stopAudio() {        
        m
if (it.isPlaying) {
        it.stop()
            }
it.release();
        mediaPlayer = null}
}

companion object {
    fun run(context: Context, audio: Int) {
    val audioHelper = AudioHelper(context)
        audioHelper.routeAudioToSpeaker()
            audioHelper.setVolume(90)
            audioHelper.playAudio(audio)
         }
    
         }
    }
    }