package com.sanin.tv.settings
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.databinding.FragmentExtensionsBinding
import com.sanin.tv.settings.paging.AnimeExtensionAdapter
import com.sanin.tv.settings.paging.AnimeExtensionsViewModel
import com.sanin.tv.settings.paging.AnimeExtensionsViewModelFactory
import com.sanin.tv.settings.paging.OnAnimeInstallClickListener
import eu.kanade.tachiyomi.extension.anime.AnimeExtensionManager
import eu.kanade.tachiyomi.extension.anime.model.AnimeExtension
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import rx.android.schedulers.AndroidSchedulers
import uy.kohesive.injekt.Injekt
import uy.kohesive.injekt.api.get
class AnimeExtensionsFragment : Fragment(),    SearchQueryHandler, OnAnimeInstallClickListener {
    private var _binding: FragmentExtensionsBinding? = null    
private val binding get() = _binding!!    
private val viewModel: AnimeExtensionsViewModel by viewModels {        
        A

private val adapter by lazy {        
        A

private val animeExtensionManager: AnimeExtensionManager = Injekt.get()    
override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View {        
        _
        binding.allExtensionsRecyclerView.adapter = adapter        binding.allExtensionsRecyclerView.layoutManager = LinearLayoutManager(context)        (binding.allExtensionsRecyclerView.layoutManager as LinearLayoutManager).isItemPrefetchEnabled =            true        lifecycleScope.launch {            viewModel.pagerFlow.collectLatest { it ->                binding.allExtensionsRecyclerView.post {                    lifecycleScope.launch {                        adapter.submitData(it)                    }}}}
viewModel.invalidatePager() // Force a refresh of the pager
return binding.root    }

override fun updateContentBasedOnQuery(query: String?) {        
        v

override fun notifyDataChanged() {        
        v

override fun onInstallClick(pkg: AnimeExtension.Available) {
    val context = requireContext()
if (isAdded) {
    val notificationManager =                requireContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
val installerSteps = InstallerSteps(notificationManager, context)            // Start the installation process            animeExtensionManager.installExtension(pkg)                .observeOn(AndroidSchedulers.mainThread())                .subscribe(                    { 
        i
)}
}

override fun onDestroyView() {        
        s
_binding = null    }}
}}
