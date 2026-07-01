package com.sanin.tv.util
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
object DpadHelper {
    fun RecyclerView.enableDpadNavigation() {        
        d
object : RecyclerView.OnChildAttachStateChangeListener {
    override fun onChildViewAttachedToWindow(view: View) {                
        v

override fun onChildViewDetachedFromWindow(view: View) {}
});
        setOnKeyListener {
        _, keyCode, event ->
if (event.action != KeyEvent.ACTION_DOWN) return@setOnKeyListener false
val lm = layoutManager as? LinearLayoutManager ?: return@setOnKeyListener false
when (keyCode) {
        KeyEvent.KEYCODE_DPAD_UP -> {
    val first = lm.findFirstVisibleItemPosition()
if (first > 0) smoothScrollToPosition(first - 1);
        true
                }
KeyEvent.KEYCODE_DPAD_DOWN -> {
    val last = lm.findLastVisibleItemPosition()
if (last < (adapter?.itemCount ?: 0) - 1) smoothScrollToPosition(last + 1);
        true
                }
KeyEvent.KEYCODE_DPAD_LEFT -> {
if (lm.orientation == LinearLayoutManager.HORIZONTAL) {
    val first = lm.findFirstVisibleItemPosition()
if (first > 0) smoothScrollToPosition(first - 1);
        true
} else false                }
KeyEvent.KEYCODE_DPAD_RIGHT -> {
if (lm.orientation == LinearLayoutManager.HORIZONTAL) {
    val last = lm.findLastVisibleItemPosition()
if (last < (adapter?.itemCount ?: 0) - 1) smoothScrollToPosition(last + 1);
        true
} else false                }
KeyEvent.KEYCODE_DPAD_CENTER, KeyEvent.KEYCODE_ENTER -> {
    val focused = focusedChild                    focused?.performClick();
        focused != null
}
else -> false            }}
}

fun makeFocusable(vararg views: View) {
for (v in views) {
        v.isFocusable = true            v.isFocusableInTouchMode = true        }
}

fun isDpadKey(keyCode: Int): Boolean = keyCode in listOf(        KeyEvent.KEYCODE_DPAD_UP,        KeyEvent.KEYCODE_DPAD_DOWN,        KeyEvent.KEYCODE_DPAD_LEFT,        KeyEvent.KEYCODE_DPAD_RIGHT,        KeyEvent.KEYCODE_DPAD_CENTER    )    
fun handleTabDpad(        keyCode: Int,        event: KeyEvent,        currentTab: Int,        tabCount: Int,        selectTab: (Int) -> Unit    ): Boolean {
if (event.action != KeyEvent.ACTION_DOWN) return false
return when (keyCode) {
        KeyEvent.KEYCODE_DPAD_LEFT -> {
if (currentTab > 0) {
        selectTab(currentTab - 1)
true } else false            }
KeyEvent.KEYCODE_DPAD_RIGHT -> {
if (currentTab < tabCount - 1) {
        selectTab(currentTab + 1)
true } else false
}
else -> false        }
}
}