package com.sanin.tv.media.anime

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.sanin.tv.R

class SelectorDialogFragment : DialogFragment() {
    private var items: Array<String> = emptyArray()
    private var selectedIndex = 0
    private var onSelected: ((Int) -> Unit)? = null

    fun setItems(items: Array<String>, selected: Int, callback: (Int) -> Unit) {
        this.items = items
        this.selectedIndex = selected
        this.onSelected = callback
    }

    
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(android.R.layout.simple_list_item_single_choice, container, false) as? android.widget.ListView
        view?.apply {
            adapter = android.widget.ArrayAdapter(requireContext(), android.R.layout.simple_list_item_single_choice, items)
            setItemChecked(selectedIndex, true)
            setOnItemClickListener {
        _, _, position, _ ->
                onSelected?.invoke(position)
                dismiss()
             }
        
             }
        }
        return view ?: super.onCreateView(inflater, container, savedInstanceState)
     }
}
