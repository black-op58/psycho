package com.sanin.tv.util

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.graphics.ColorUtils
import com.google.android.material.button.MaterialButton
import com.sanin.tv.R

/**
 * Inline replacement for eltos SimpleDialogFragments color picker.
 * Uses AlertDialog with preset color grids.
 */
object SimpleColorPicker {

    interface OnDialogResultListener {
        fun onDialogResult(dialogTag: String, which: Int, extras: Bundle): Boolean
    }

    const val BUTTON_POSITIVE = -1
    const val COLOR = "color"

    private val PRESET_COLORS = intArrayOf(
        0xFFF44336.toInt(), 0xFFE91E63.toInt(), 0xFF9C27B0.toInt(), 0xFF673AB7.toInt(),
        0xFF3F51B5.toInt(), 0xFF2196F3.toInt(), 0xFF03A9F4.toInt(), 0xFF00BCD4.toInt(),
        0xFF009688.toInt(), 0xFF4CAF50.toInt(), 0xFF8BC34A.toInt(), 0xFFCDDC39.toInt(),
        0xFFFFEB3B.toInt(), 0xFFFFC107.toInt(), 0xFFFF9800.toInt(), 0xFFFF5722.toInt(),
        0xFF795548.toInt(), 0xFF9E9E9E.toInt(), 0xFF607D8B.toInt(), 0xFF000000.toInt(),
        0xFFFFFFFF.toInt(),
    )

    fun showColorDialog(
        activity: Activity,
        title: CharSequence?,
        presetColors: IntArray = PRESET_COLORS,
        selectedColor: Int = Color.WHITE,
        dialogTag: String = "",
        @androidx.annotation.StyleRes themeResId: Int = 0,
    ): Dialog {
        val builder = if (themeResId != 0)
            AlertDialog.Builder(activity, themeResId)
        else
            AlertDialog.Builder(activity)

        val density = activity.resources.displayMetrics.density
        val padding = (16 * density).toInt()

        val container = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(padding, padding, padding, padding)
        }

        if (title != null) {
            container.addView(TextView(activity).apply {
                text = title
                textSize = 18f
                setPadding(0, 0, 0, (12 * density).toInt())
            })
        }

        val cols = 5
        val buttonSize = (54 * density).toInt()
        val margin = (6 * density).toInt()

        var row = LinearLayout(activity).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
        }
        container.addView(row)

        for ((i, color) in presetColors.withIndex()) {
            val btn = MaterialButton(activity).apply {
                layoutParams = LinearLayout.LayoutParams(buttonSize, buttonSize).apply {
                    setMargins(margin, margin, margin, margin)
                }
                setBackgroundColor(color)
                val drawable = GradientDrawable().apply {
                    shape = GradientDrawable.OVAL
                    setColor(color)
                    setStroke(2, if (color == Color.WHITE) Color.LTGRAY else Color.TRANSPARENT)
                }
                background = drawable
                setOnClickListener {
                    val result = Bundle().apply { putInt(COLOR, color) }
                    if (activity is OnDialogResultListener) {
                        activity.onDialogResult(dialogTag, BUTTON_POSITIVE, result)
                    }
                    (it.context as? Activity)?.let { act ->
                        (it.rootView.parent as? View)?.let { parent ->
                            val dialog = parent.parent as? Dialog ?: parent as? Dialog
                            dialog?.dismiss()
                        }
                    }
                    try {
                        (activity as? Activity)?.let {
                            // Find the dialog window and dismiss it
                            it.currentFocus?.let { focus ->
                                val dialog = focus.rootView.parent?.parent
                                if (dialog is Dialog) dialog.dismiss()
                            }
                        }
                    } catch (_: Exception) {}
                }
                if (i > 0 && i % cols == 0) {
                    row = LinearLayout(activity).apply {
                        orientation = LinearLayout.HORIZONTAL
                        layoutParams = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                        )
                    }
                    container.addView(row)
                }
            }
            row.addView(btn)
        }

        builder.setView(container)
        builder.setNegativeButton(android.R.string.cancel, null)
        return builder.show()
    }

    fun showColorWheelDialog(
        activity: Activity,
        title: CharSequence?,
        originalColor: Int = Color.WHITE,
        alpha: Boolean = true,
        dialogTag: String = "",
        @androidx.annotation.StyleRes themeResId: Int = 0,
    ): Dialog {
        return showColorDialog(activity, title, PRESET_COLORS, originalColor, dialogTag, themeResId)
    }
}
