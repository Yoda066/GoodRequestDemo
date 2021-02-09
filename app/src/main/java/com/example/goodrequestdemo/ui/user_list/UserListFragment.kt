package com.example.goodrequestdemo.ui.user_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodrequestdemo.R
import com.example.goodrequestdemo.databinding.UserListFragmentBinding
import kotlinx.android.synthetic.main.user_list_fragment.*

class UserListFragment : Fragment(R.layout.user_list_fragment) {

    private lateinit var usersViewModel: UserListViewModel

    private lateinit var userListAdapter: UserListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel = ViewModelProviders.of(requireActivity()).get(UserListViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        userListAdapter = UserListAdapter()

        userList.apply {
            adapter = userListAdapter
            addItemDecoration(DividerItemDecoration(context, LinearLayoutManager.VERTICAL))
        }

        usersViewModel.userLiveData.observe(viewLifecycleOwner) {
            userListAdapter.submitList(it)
        }

        swipeLayout.setOnRefreshListener {
            userListAdapter.submitList(null)
            usersViewModel.refresh()
            swipeLayout.isRefreshing = false
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return UserListFragmentBinding.inflate(
            LayoutInflater.from(context),
            null,
            false
        ).apply {
            userListViewModel = this@UserListFragment.usersViewModel
            lifecycleOwner = viewLifecycleOwner
        }.root
    }
}