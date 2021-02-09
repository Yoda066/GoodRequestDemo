package com.example.goodrequestdemo.ui.user_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.goodrequestdemo.R
import com.example.goodrequestdemo.databinding.UserDetailFragmentBinding
import kotlinx.android.synthetic.main.user_list_fragment.*

class UserFragment(private val userId: Long? = null) : Fragment(R.layout.user_detail_fragment) {

    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return UserDetailFragmentBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        ).apply {
            userViewModel = this@UserFragment.userViewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userId?.let {
            userViewModel.loadUser(it)
        }
        super.onViewCreated(view, savedInstanceState)
    }
}