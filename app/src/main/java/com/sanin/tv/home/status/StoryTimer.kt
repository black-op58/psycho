package com.sanin.tv.home.status
import android.os.CountDownTimer
class StoryTimer(    
private val updateInterval: Long) {
    private lateinit var timer: CountDownTimer    
private var prevVal = 0    
private var pauseLength = 0L
var onTimerCompleted: () -> Unit = {}

var percentTick: (Int) -> Unit = {}

var timeLeft: Long = 0        
private set
fun start(durationInMillis: Long = updateInterval) {        
        c
object : CountDownTimer(durationInMillis, 1) {
    override fun onTick(millisUntilFinished: Long) {                
        t
val percent =                    ((pauseLength + durationInMillis - millisUntilFinished) * 100 / (pauseLength + durationInMillis)).toInt()
if (percent != prevVal) {
        percentTick.invoke(percent);
        prevVal = percent
                }
}

override fun onFinish() {                
        o
            }
            }
timer.start()
     }
fun cancel() {
if (::timer.isInitialized) {
        timer.cancel()
        }
}

fun pause() {
if (::timer.isInitialized) {
        timer.cancel();
        pauseLength = updateInter
val - timeLeft        }
}

fun resume() {
if (::timer.isInitialized && timeLeft > 0) {
        start(timeLeft)
        timer.start()
         }
}

fun setOnTimerCompletedListener(onTimerCompleted: () -> Unit) {        
        t

fun setOnPercentTickListener(percentTick: (Int) -> Unit) {        
        t