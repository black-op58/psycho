package com.sanin.tv.media

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.App
import com.sanin.tv.Mapper
import com.sanin.tv.client
import com.sanin.tv.databinding.DialogSourceSearchBinding
import com.sanin.tv.px
import com.sanin.tv.settings.saving.PrefManager
import com.sanin.tv.settings.saving.PrefName
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.math.clamp

class SourceSearchDialogFragment : DialogFragment() {
    private var _binding: DialogSourceSearchBinding? = null
    private val binding get() = _binding!!
    private val searchResults = mutableListOf<SearchResultItem>()
    private var adapter: androidx.recyclerview.widget.RecyclerView.Adapter<*>? = null

    data class SearchResultItem(val title: String, val coverUrl: String?, val id: Int)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = DialogSourceSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isCancelable = true

        binding.sourceSearchInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(binding.sourceSearchInput.text.toString())
                true
            } else false
        }

        binding.sourceSearchResults.layoutManager = LinearLayoutManager(requireContext())
        adapter = object : androidx.recyclerview.widget.RecyclerView.Adapter<SearchResultViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchResultViewHolder {
                val textView = android.widget.TextView(parent.context).apply {
                    layoutParams = ViewGroup.MarginLayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        48.dpToPx()
                    )
                    setPadding(16.dpToPx(), 0, 16.dpToPx(), 0)
                    gravity = android.view.Gravity.CENTER_VERTICAL
                    fontFamily = androidx.core.content.res.ResourcesCompat.getFont(parent.context, com.sanin.tv.R.font.poppins)
                    setTextColor(androidx.core.content.ContextCompat.getColor(parent.context, com.sanin.tv.R.color.bg_white))
                    setBackgroundResource(com.sanin.tv.R.drawable.ui_bg)
                }
                return SearchResultViewHolder(textView)
            }
            override fun onBindViewHolder(holder: SearchResultViewHolder, position: Int) {
                holder.bind(searchResults[position])
            }
            override fun getItemCount() = searchResults.size
        }
        binding.sourceSearchResults.adapter = adapter
    }

    private fun performSearch(query: String) {
        if (query.isBlank()) return
        lifecycleScope.launch {
            searchResults.clear()
            adapter?.notifyDataSetChanged()
            // Simple search using AniList API
            try {
                val results = withContext(Dispatchers.IO) { 
        s
                searchResults.addAll(results)
                adapter?.notifyDataSetChanged()
            } catch (e: Exception) {
                // Silently handle
            }
        }
    }

    private fun searchAnilist(query: String): List<SearchResultItem> {
        return try {
            val graphql = """{"query":"query(\$search: String) { 
        P
            val response = client.post("https://graphql.anilist.co") {
                body(graphql)
                header("Content-Type", "application/json")
                header("Accept", "application/json")
            }
            val json = Mapper.json.parseToJsonElement(response.text)
            val mediaList = json.jsonObject["data"]?.jsonObject?.get("Page")?.jsonObject?.get("media")?.jsonArray
            mediaList?.map { element ->
                val obj = element.jsonObject
                val id = obj["id"]?.jsonPrimitive?.content?.toIntOrNull() ?: 0
                val titles = obj["title"]?.jsonObject
                val title = titles?.get("romaji")?.jsonPrimitive?.contentOrNull
                    ?: titles?.get("english")?.jsonPrimitive?.contentOrNull
                    ?: "Unknown"
                val cover = obj["coverImage"]?.jsonObject?.get("large")?.jsonPrimitive?.contentOrNull
                SearchResultItem(title, cover, id)
            } ?: emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun dismiss() {
        super.dismiss()
    }

    private fun Int.dpToPx(): Int = (this * resources.displayMetrics.density).toInt()
}

class SearchResultViewHolder(itemView: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {
    fun bind(item: SourceSearchDialogFragment.SearchResultItem) {
        (itemView as? android.widget.TextView)?.text = item.title
        itemView.setOnClickListener {
            // Notify parent of selection
            // The source search dialog result would be handled by the caller
            dismiss()
        }
    }

    private fun dismiss() {
        val dialog = itemView.context as? androidx.fragment.app.FragmentActivity
        val fragment = dialog?.supportFragmentManager?.findFragmentByTag("source_search")
        if (fragment is DialogFragment) fragment.dismiss()
    }
}
