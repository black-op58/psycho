package com.sanin.tv.media

import android.os.Bundle
import android.view.KeyEvent
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.R
import com.sanin.tv.databinding.ActivitySearchBinding
import com.sanin.tv.navBarHeight
import com.sanin.tv.statusBarHeight
import com.sanin.tv.themes.ThemeManager

class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ThemeManager(this).applyTheme()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchRecyclerView.apply {
            updatePadding(top = statusBarHeight, bottom = navBarHeight)
            layoutManager = LinearLayoutManager(this@SearchActivity)
            isFocusable = true
            isFocusableInTouchMode = false
            setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_BACK) {
        finish(); true
                } else false
            }
            // Placeholder - will display search results
            adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<SearchHeaderViewHolder>() {
                override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHeaderViewHolder {
                    return SearchHeaderViewHolder(
                        androidx.appcompat.widget.AppCompatTextView(parent.context).apply {
                            layoutParams = ViewGroup.MarginLayoutParams(
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT
                            )
                            setPadding(32, 32, 32, 32)
                            text = getString(R.string.search_hint)
                            setTextColor(androidx.core.content.ContextCompat.getColor(this@SearchActivity, R.color.bg_white))
                            typeface = androidx.core.content.res.ResourcesCompat.getFont(this@SearchActivity, R.font.poppins)
                         }
                    )
                 }
                override fun onBindViewHolder(holder: SearchHeaderViewHolder, position: Int) {}
                override fun getItemCount() = 1
            }
        }
    }
}

class SearchHeaderViewHolder(itemView: android.view.View) :
    androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView)
