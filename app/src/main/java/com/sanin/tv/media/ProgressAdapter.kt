package com.sanin.tv.media
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.sanin.tv.GesturesListener
import com.sanin.tv.R
import com.sanin.tv.currContext
import com.sanin.tv.databinding.ItemProgressbarBinding
import com.sanin.tv.snackString
class ProgressAdapter(
private val horizontal: Boolean = true, searched: Boolean) :    RecyclerView.Adapter<ProgressAdapter.ProgressViewHolder>() {
    val ready = MutableLiveData(searched)    
var bar: ProgressBar? = null    
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProgressViewHolder {
    val binding =            ItemProgressbarBinding.inflate(LayoutInflater.from(parent.context), parent, false)
return ProgressViewHolder(binding)    }

@SuppressLint("ClickableViewAccessibility")    
override fun onBindViewHolder(holder: ProgressViewHolder, position: Int) {
    val progressBar = holder.binding.root        bar = progressBar
val doubleClickDetector = GestureDetector(progressBar.context, 
object : GesturesListener() {
    override fun onDoubleClick(event: MotionEvent) {                
        s

override fun onScrollYClick(y: Float) {}

override fun onSingleClick(event: MotionEvent) {}
})        progressBar.setOnTouchListener { v, event ->
            doubleClickDetector.onTouchEvent(event)
        v.performClick()            true
        }
if (ready.value == false) {            ready.postValue(true)        }
}

override fun getItemCount(): Int = 1    inner 
class ProgressViewHolder(
val binding: ItemProgressbarBinding) :        RecyclerView.ViewHolder(binding.root) {        
        i
}}