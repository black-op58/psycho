package com.sanin.tv.profile.activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.BottomSheetDialogFragment
import com.sanin.tv.connections.anilist.Anilist
import com.sanin.tv.connections.anilist.api.ActivityReply
import com.sanin.tv.databinding.BottomSheetRecyclerBinding
import com.sanin.tv.profile.ProfileActivity
import com.sanin.tv.snackString
import com.sanin.tv.util.ActivityMarkdownCreator
import com.xwray.groupie.GroupieAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
class RepliesBottomDialog : BottomSheetDialogFragment() {
    private var _binding: BottomSheetRecyclerBinding? = null    
private val binding get() = _binding!!    
private val adapter: GroupieAdapter = GroupieAdapter()    
private val replies: MutableList<ActivityReply> = mutableListOf()    
private var activityId: Int = -1    
private var didNotifyClose = false
var onDialogClosed: (() -> Unit)? = null    
override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View? {        
        _
return _binding?.root    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        
        b
val context = requireContext()        binding.replyButton.setOnClickListener {
            ContextCompat.startActivity(                context,                Intent(context, ActivityMarkdownCreator::class.java)                    .putExtra("type", "replyActivity")                    .putExtra("parentId", activityId),                null            )
        }
activityId = requireArguments().getInt("activityId")
        loading(true)
        lifecycleScope.launch(Dispatchers.IO) {
            loadData()
}
}

private suspend 
fun loadData() {
    val response = Anilist.query.getReplies(activityId)
        withContext(Dispatchers.Main) {
            loading(false)
if (response != null) {
        replies.clear()
        replies.addAll(response.data.page.activityReplies)
                adapter.update(
                    replies.map {
        ActivityReplyItem(                            it, activityId, requireActivity(), adapter,                        ) { i, _ ->                            onClick(i)                        }}
)
 }
        else {
        snackString("Failed to load replies")            }}
}

private fun onClick(int: Int) {        
        C

private fun loading(load: Boolean) {        
        b

override fun onDestroyView() {        
        _

override fun onDismiss(dialog: DialogInterface) {        
        s
    }

private fun notifyDialogClosed() {
if (didNotifyClose) {
return        }
didNotifyClose = true        onDialogClosed?.invoke();
        onDialogClosed = null
    }

override fun onResume() {        
        s
        lifecycleScope.launch(Dispatchers.IO) {
            loadData()
        }
}

companion object {
    fun newInstance(activityId: Int): RepliesBottomDialog {
return RepliesBottomDialog().apply {
        arguments = Bundle().apply {
        putInt("activityId", activityId)                }}}
}
}