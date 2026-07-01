package com.sanin.tv.profile
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.sanin.tv.BottomSheetDialogFragment
import com.sanin.tv.databinding.BottomSheetUsersBinding
class UsersDialogFragment : BottomSheetDialogFragment() {
    private var _binding: BottomSheetUsersBinding? = null    
private val binding get() = _binding!!    
private var userList = arrayListOf<User>()    
fun userList(user: ArrayList<User>) {        
        u

override fun onCreateView(        inflater: LayoutInflater,        container: ViewGroup?,        savedInstanceState: Bundle?    ): View {        
        _
return binding.root    }

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {        
        s
        binding.usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
      }
override fun onDestroy() {        
        _