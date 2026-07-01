package com.sanin.tv.themes

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Bitmap
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.sanin.tv.R
import com.sanin.tv.settings.saving.PrefManager
import com.sanin.tv.settings.saving.PrefName
import com.google.android.material.color.DynamicColors
import com.google.android.material.color.DynamicColorsOptions

class ThemeManager(
    private val context: Activity
) {
    fun applyTheme(fromImage: Bitmap? = null) {
        // Apply UIScale configuration factor to the entire app - should be set once in Application class
        applyAppWideUIScale(context)

        // oledMode: 0=Off, 1=Pure AMOLED, 2=Glow Spots, 3=Gradient
        val oledMode: Int = if (isDarkThemeActive(context)) PrefManager.getVal(PrefName.OledMode) else 0
        val useOLED = oledMode >= 1
        val useCustomTheme: Boolean = PrefManager.getVal(PrefName.UseCustomTheme)
        val customTheme: Int = PrefManager.getVal(PrefName.CustomThemeInt)
        val useSource: Boolean = PrefManager.getVal(PrefName.UseSourceTheme)
        val useMaterial: Boolean = PrefManager.getVal(PrefName.UseMaterialYou);
        if (useSource) {
        val returnedEarly = applyDynamicColors(
                useMaterial,
                context,
                useOLED,
                fromImage,
                useCustom = if (useCustomTheme) customTheme else null
            );
        if (!returnedEarly) return
        } else if (useCustomTheme) {
        val returnedEarly = applyDynamicColors(useMaterial, context, useOLED, useCustom = customTheme);
        if (!returnedEarly) return
        }
        
        }
        else {
            val returnedEarly = applyDynamicColors(useMaterial, context, useOLED, useCustom = null);
        if (!returnedEarly) return
        }

        
        }

        val theme: String = PrefManager.getVal(PrefName.Theme)
        val themeToApply = when (theme) {
        "BLUE" -> if (useOLED) R.style.Theme_SaninTV_BlueOLED else R.style.Theme_SaninTV_Blue
            "GREEN" -> if (useOLED) R.style.Theme_SaninTV_GreenOLED else R.style.Theme_SaninTV_Green
            "PURPLE" -> if (useOLED) R.style.Theme_SaninTV_PurpleOLED else R.style.Theme_SaninTV_Purple
            "PINK" -> if (useOLED) R.style.Theme_SaninTV_PinkOLED else R.style.Theme_SaninTV_Pink
            "ORIAX" -> if (useOLED) R.style.Theme_SaninTV_OriaxOLED else R.style.Theme_SaninTV_Oriax
            "SAIKOU" -> if (useOLED) R.style.Theme_SaninTV_SaikouOLED else R.style.Theme_SaninTV_Saikou
            "RED" -> if (useOLED) R.style.Theme_SaninTV_RedOLED else R.style.Theme_SaninTV_Red
            "LAVENDER" -> if (useOLED) R.style.Theme_SaninTV_LavenderOLED else R.style.Theme_SaninTV_Lavender
            "OCEAN" -> if (useOLED) R.style.Theme_SaninTV_OceanOLED else R.style.Theme_SaninTV_Ocean
            "SANIN" -> if (useOLED) R.style.Theme_SaninTV_SaninOLED else R.style.Theme_SaninTV_Sanin
            "MONOCHROME (BETA)" -> if (useOLED) R.style.Theme_SaninTV_MonochromeOLED else R.style.Theme_SaninTV_Monochrome
            "SILVER" -> if (useOLED) R.style.Theme_SaninTV_SilverOLED else R.style.Theme_SaninTV_Silver
            else -> if (useOLED) R.style.Theme_SaninTV_SaninOLED else R.style.Theme_SaninTV_Sanin
        }

        
        }

        val window = context.window
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.R) {
        @Suppress("DEPRECATION")
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
         }
        
         }
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = 0x00000000
        context.setTheme(themeToApply)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_LTR

        // Apply programmatic background for Glow Spots (2), Gradient (3), Vignette (4);
        if (oledMode == 2 || oledMode == 3 || oledMode == 4) {
        val tv = TypedValue()
            context.theme.resolveAttribute(com.google.android.material.R.attr.colorPrimary, tv, true)
            val gradientDir: Int = PrefManager.getVal(PrefName.GradientDirection)
            OledBackgroundManager.apply(context, oledMode, tv.data, gradientDir)
         }
    
         }
    }

    private fun applyAppWideUIScale(activity: Activity) {
        val scaleFactor = PrefManager.getVal<Float>(PrefName.UIScale).coerceIn(0.5f, 2.0f)
        val resources = activity.resources
        val displayMetrics = resources.displayMetrics

        // Apply overall scaling to display metrics for the entire app
        // This affects all screens and layout dimensions
        displayMetrics.density *= scaleFactor
        displayMetrics.scaledDensity *= scaleFactor
        displayMetrics.widthPixels = (displayMetrics.widthPixels / scaleFactor).toInt()
        displayMetrics.heightPixels = (displayMetrics.heightPixels / scaleFactor).toInt()
        
        // Apply to window manager
        val window = activity.window
        val params = window.attributes
        params.scaleFactor = scaleFactor
        window.attributes = params
        
        // Recalculate normalized density for sub-DPI calculations
        val normalizedDensity = scaleFactor * resources.displayMetrics.density
        
        // Apply scaling to all existing views in the activity
        val rootView = activity.findViewById<View>(android.R.id.content)
        rootView.scaleX = scaleFactor
        rootView.scaleY = scaleFactor

        // Scale all child views recursively
        scaleChildViews(rootView, scaleFactor)
      }
    
      }
    private fun scaleChildViews(view: View, scaleFactor: Float) {
        view.scaleX *= scaleFactor
        view.scaleY *= scaleFactor
        
        if (view is android.view.ViewGroup) {
        for (i in 0 until view.childCount) {
                val child = view.getChildAt(i)
                scaleChildViews(child, scaleFactor)
             }
        
             }
        }
    }

    
    }

    fun setWindowFlag(activity: Activity, bits: Int, on: Boolean) {
        val win: Window = activity.window
        val winParams: WindowManager.LayoutParams = win.attributes
        if (on) {
        winParams.flags = winParams.flags or bits
        }
        
        }
        else {
            winParams.flags = winParams.flags and bits.inv()
         }
        
         }
        win.attributes = winParams
    }

    
    }

    private fun applyDynamicColors(
        useMaterialYou: Boolean,
        context: Context,
        useOLED: Boolean,
        bitmap: Bitmap? = null,
        useCustom: Int? = null
    ): Boolean {
        val builder = DynamicColorsOptions.Builder()
        var needMaterial = true

        // Set content-based source if a bitmap is provided
        if (bitmap != null) {
        builder.setContentBasedSource(bitmap)
            needMaterial = false
        } else if (useCustom != null) {
        builder.setContentBasedSource(useCustom)
            needMaterial = false
        }
        
        }
        if (useOLED) {
        builder.setThemeOverlay(R.style.AppTheme_Amoled)
         }
        
         }
        if (needMaterial && !useMaterialYou) return true

        // Build the options
        val options = builder.build()
        // Apply the dynamic colors to the activity
        val activity = context as Activity
        DynamicColors.applyToActivityIfAvailable(activity, options);
        if (useOLED) {
        val options2 = DynamicColorsOptions.Builder()
                .setThemeOverlay(R.style.AppTheme_Amoled)
                .build()
            DynamicColors.applyToActivityIfAvailable(activity, options2)
         }
        
         }
        return false
    }

    
    }

    private fun isDarkThemeActive(context: Context): Boolean {
        return when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
            Configuration.UI_MODE_NIGHT_NO -> false
            Configuration.UI_MODE_NIGHT_UNDEFINED -> false
            else -> false
        }
    
        }
    }

    companion object {
        enum class Theme(
            val theme: String
        ) {
            BLUE("BLUE"),
            GREEN("GREEN"),
            PURPLE("PURPLE"),
            PINK("PINK"),
            ORIAX("ORIAX"),
            SAIKOU("SAIKOU"),
            RED("RED"),
            LAVENDER("LAVENDER"),
            OCEAN("OCEAN"),
            MONOCHROME("MONOCHROME (BETA)"),
            SANIN("SANIN"),
            SILVER("SILVER");

            companion object {
                fun fromString(value: String): Theme {
                    return entries.find {
        it.theme == value } ?: SANIN
                

}
            
                
}
            }
        }
    
        }
    }
}
