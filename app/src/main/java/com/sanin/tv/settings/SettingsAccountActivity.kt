package com.sanin.tv.settings

import android.content.Intent
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.R
import com.sanin.tv.connections.anilist.Anilist
import com.sanin.tv.connections.mal.MAL
import com.sanin.tv.databinding.ActivitySettingsAccountsBinding
import com.sanin.tv.initActivity
import com.sanin.tv.loadImage
import com.sanin.tv.navBarHeight
import com.sanin.tv.openLinkInBrowser
import com.sanin.tv.others.CustomBottomDialog
import com.sanin.tv.settings.saving.PrefManager
import com.sanin.tv.settings.saving.PrefName
import com.sanin.tv.snackString
import com.sanin.tv.startMainActivity
import com.sanin.tv.statusBarHeight
import com.sanin.tv.themes.ThemeManager
import io.noties.markwon.Markwon
import io.noties.markwon.SoftBreakAddsNewLinePlugin
import kotlinx.coroutines.launch

class SettingsAccountActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingsAccountsBinding

    private val restartMainActivity =
        object : OnBackPressedCallback(false) {
            override fun handleOnBackPressed() = startMainActivity(this@SettingsAccountActivity)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager(this).applyTheme()
        initActivity(this)

        val context = this
        binding = ActivitySettingsAccountsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            settingsAccountsLayout.updateLayoutParams<ViewGroup.MarginLayoutParams> {
                topMargin = statusBarHeight
                bottomMargin = navBarHeight
            }

            accountSettingsBack.setOnClickListener { onBackPressedDispatcher.onBackPressed() }

            settingsAccountHelp.setOnClickListener {
                CustomBottomDialog.newInstance().apply {
                    setTitleText(context.getString(R.string.account_help))
                    addView(
                        TextView(it.context).apply {
                            val markWon = Markwon.builder(it.context)
                                .usePlugin(SoftBreakAddsNewLinePlugin.create()).build()
                            markWon.setMarkdown(this, context.getString(R.string.full_account_help))
                        }
                    )
                }.show(supportFragmentManager, "dialog")
            }

            // AniList card click -> login/logout
            settingsAnilistCard.setOnClickListener {
                if (Anilist.token == null) {
                    Anilist.loginIntent(context)
                } else {
                    Anilist.removeSavedToken()
                    restartMainActivity.isEnabled = true
                    reload()
                }
            }

            settingsAnilistLoginAction.setOnClickListener {
                settingsAnilistCard.performClick()
            }

            // MAL login action
            settingsMalLoginAction.setOnClickListener {
                if (MAL.token == null) {
                    MAL.loginIntent(context)
                } else {
                    MAL.removeSavedToken()
                    restartMainActivity.isEnabled = true
                    reload()
                }
            }

            reload()

            // Avatar click -> open profile
            profileAvatar.setOnClickListener {
                if (Anilist.token != null) {
                    it.performHapticFeedback(HapticFeedbackConstants.LONG_PRESS)
                    val anilistLink = getString(
                        R.string.anilist_link,
                        PrefManager.getVal<String>(PrefName.AnilistUserName)
                    )
                    openLinkInBrowser(anilistLink)
                }
            }
        }

        binding.settingsRecyclerView.adapter = SettingsAdapter(
            arrayListOf(
                Settings(
                    type = 1,
                    name = getString(R.string.anilist_settings),
                    desc = getString(R.string.alsettings_desc),
                    icon = R.drawable.ic_anilist,
                    onClick = {
                        lifecycleScope.launch {
                            Anilist.query.getUserData()
                            startActivity(Intent(context, AnilistSettingsActivity::class.java))
                        }
                    },
                    isActivity = true
                ),
                Settings(
                    type = 2,
                    name = getString(R.string.comments_button),
                    desc = getString(R.string.comments_button_desc),
                    icon = R.drawable.ic_round_comment_24,
                    isChecked = PrefManager.getVal<Int>(PrefName.CommentsEnabled) == 1,
                    switch = { isChecked, _ ->
                        PrefManager.setVal(PrefName.CommentsEnabled, if (isChecked) 1 else 2)
                        reload()
                    },
                    isVisible = Anilist.token != null
                )
            )
        )
        binding.settingsRecyclerView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    private fun reload() {
        binding.apply {
            if (Anilist.token != null) {
                // Update banner
                if (Anilist.bg != null) {
                    profileBannerImage.visibility = View.VISIBLE
                    profileBannerScrim.visibility = View.VISIBLE
                    profileBannerImage.loadImage(Anilist.bg)
                } else {
                    profileBannerImage.visibility = View.VISIBLE
                    profileBannerScrim.visibility = View.VISIBLE
                    profileBannerImage.setImageResource(R.drawable.ic_anilist)
                    profileBannerImage.setBackgroundColor(
                        androidx.core.content.ContextCompat.getColor(this@SettingsAccountActivity, R.color.nav_bg)
                    )
                }

                // Update username
                profileUsername.text = Anilist.username ?: getString(R.string.username)
                profileUsername.visibility = View.VISIBLE

                // Token expiry
                val daysLeft = Anilist.getTokenExpiryDays()
                if (daysLeft != null) {
                    profileTokenExpiry.visibility = View.VISIBLE
                    profileTokenExpiry.text = when {
                        daysLeft <= 0 -> getString(R.string.reconnect_now)
                        else -> getString(R.string.reconnect_in, daysLeft)
                    }
                    profileTokenExpiry.setOnClickListener {
                        Anilist.loginIntent(this@SettingsAccountActivity)
                    }
                } else {
                    profileTokenExpiry.visibility = View.GONE
                }

                // Update avatar
                profileAvatar.loadImage(Anilist.avatar)

                // AniList card
                settingsAnilistLoginStatus.text = getString(R.string.anilist)
                settingsAnilistLoginAction.text = getString(R.string.logout)
                settingsAnilistLoginAction.visibility = View.VISIBLE

                // AniList user info
                settingsAnilistUserInfo.visibility = View.VISIBLE
                settingsAnilistUsernameDisplay.text = Anilist.username ?: ""

                if (Anilist.avatar != null) {
                    settingsAnilistAvatar.visibility = View.VISIBLE
                    settingsAnilistAvatar.loadImage(Anilist.avatar)
                } else {
                    settingsAnilistAvatar.visibility = View.GONE
                }

                // Token in card
                val cardDaysLeft = Anilist.getTokenExpiryDays()
                if (cardDaysLeft != null) {
                    settingsAnilistTokenExpiryDisplay.visibility = View.VISIBLE
                    settingsAnilistTokenExpiryDisplay.text = when {
                        cardDaysLeft <= 0 -> getString(R.string.reconnect_now)
                        else -> getString(R.string.reconnect_in, cardDaysLeft)
                    }
                } else {
                    settingsAnilistTokenExpiryDisplay.visibility = View.GONE
                }

                // MAL section
                settingsMalLoginStatus.text = getString(R.string.myanimelist)
                if (MAL.token != null) {
                    settingsMalLoginAction.text = getString(R.string.logout)
                } else {
                    settingsMalLoginAction.text = getString(R.string.login)
                }
                settingsMalLoginAction.visibility = View.VISIBLE

                // Show settings
                settingsRecyclerView.visibility = View.VISIBLE

            } else {
                // Not logged in
                profileUsername.visibility = View.GONE
                profileTokenExpiry.visibility = View.GONE
                profileBannerImage.visibility = View.VISIBLE
                profileBannerScrim.visibility = View.VISIBLE
                profileBannerImage.setImageResource(0)
                profileBannerImage.setBackgroundColor(
                    androidx.core.content.ContextCompat.getColor(this@SettingsAccountActivity, R.color.nav_bg)
                )
                profileAvatar.setImageResource(R.drawable.ic_round_person_24)

                // AniList card - show login
                settingsAnilistLoginStatus.text = getString(R.string.anilist)
                settingsAnilistLoginAction.text = getString(R.string.login)
                settingsAnilistLoginAction.visibility = View.VISIBLE
                settingsAnilistUserInfo.visibility = View.GONE
                settingsAnilistAvatar.visibility = View.GONE

                // MAL card
                settingsMalLoginStatus.text = getString(R.string.myanimelist)
                settingsMalLoginAction.text = getString(R.string.login)
                settingsMalLoginAction.visibility = View.VISIBLE

                settingsRecyclerView.visibility = View.GONE
            }
        }
    }
}
