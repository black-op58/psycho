package com.sanin.tv.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.sanin.tv.databinding.BottomSheetRecyclerBinding
import com.sanin.tv.notifications.subscription.SubscriptionHelper

class SubscriptionsBottomDialog : BottomSheetDialogFragment() {

    private var _binding: BottomSheetRecyclerBinding? = null
    private val binding get() = _binding!!

    var subscriptions: Map<Int, SubscriptionHelper.Companion.SubscribeMedia> = emptyMap()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(subscriptions: Map<Int, SubscriptionHelper.Companion.SubscribeMedia>): SubscriptionsBottomDialog {
            val dialog = SubscriptionsBottomDialog()
            dialog.subscriptions = subscriptions
            return dialog
        }
    }
}
