package com.sanin.tv.settings

import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.R
import com.sanin.tv.databinding.ActivitySettingsNotificationsBinding
import com.sanin.tv.initActivity
import com.sanin.tv.navBarHeight
import com.sanin.tv.settings.saving.PrefManager
import com.sanin.tv.settings.saving.PrefName
import com.sanin.tv.statusBarHeight
import com.sanin.tv.themes.ThemeManager

class SettingsNotificationActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsNotificationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager(this).applyTheme()
        initActivity(this)
        
        binding = ActivitySettingsNotificationsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.apply {
            notificationsLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = statusBarHeight
                bottomMargin = navBarHeight
            }
            
            notificationsBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
            }
            
            onBackPressedDispatcher.addCallback(this@SettingsNotificationActivity,
                object : OnBackPressedCallback(true) {
                    override fun handleOnBackPressed() = finish()
                }
            )
            
            // Setup notification preferences list
            notificationsRecyclerView.apply {
                adapter = SettingsAdapter(arrayListOf(
                    Settings(
                        type = 2,
                        name = "AniList Notifications",
                        desc = "Receive notifications from AniList",
                        icon = R.drawable.ic_round_notifications_none_24,
                        isChecked = PrefManager.getVal(PrefName.AnilistNotifications),
                        switch = { isChecked, _ ->
                            PrefManager.setVal(PrefName.AnilistNotifications, isChecked)
                        }
                    ),
                    Settings(
                        type = 2,
                        name = "Episode Notifications",
                        desc = "Get notified when new episodes air",
                        icon = R.drawable.ic_round_new_releases_24,
                        isChecked = PrefManager.getVal(PrefName.EpisodeNotifications),
                        switch = { isChecked, _ ->
                            PrefManager.setVal(PrefName.EpisodeNotifications, isChecked)
                        }
                    ),
                ))
                layoutManager = LinearLayoutManager(this@SettingsNotificationActivity)
            }
        }
    }
}
