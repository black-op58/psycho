package com.sanin.tv.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sanin.tv.R
import com.sanin.tv.settings.saving.PrefManager
import com.sanin.tv.settings.saving.PrefName

class AddRepositoryBottomSheet : BottomSheetDialogFragment() {
    private var onRepoAdded: ((String) -> Unit)? = null

    fun setOnRepoAddedListener(listener: (String) -> Unit) {
        onRepoAdded = listener
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.dialog_add_repository, container, false)
      }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val input = view.findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.repoUrlInput)
        view.findViewById<View>(R.id.repoAddButton).setOnClickListener {
            val url = input?.text?.toString()?.trim() ?: ""
            if (url.isNotBlank()) {
                val repos = PrefManager.getVal<String>(PrefName.ExtensionRepos)
                val updatedRepos = if (repos.isBlank()) url else "$repos,$url"
                PrefManager.setVal(PrefName.ExtensionRepos, updatedRepos)
                onRepoAdded?.invoke(url)
                Toast.makeText(requireContext(), R.string.repository_added, Toast.LENGTH_SHORT).show()
                dismiss()
             }
        }
        view.findViewById<View>(R.id.repoCancelButton).setOnClickListener { dismiss()
 }
    }
}
