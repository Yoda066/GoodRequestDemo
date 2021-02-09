package com.example.goodrequestdemo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProviders
import com.example.goodrequestdemo.ui.user_detail.UserFragment
import com.example.goodrequestdemo.ui.user_list.UserListFragment
import com.example.goodrequestdemo.ui.user_list.UserListViewModel
import com.example.goodrequestdemo.util.EventObserver
import com.example.goodrequestdemo.util.NavigationManager

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var usersViewModel: UserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        usersViewModel = ViewModelProviders.of(this).get(UserListViewModel::class.java)

        NavigationManager.loadUserId.observe(this, EventObserver { userId ->
            supportFragmentManager.commit {
                val fragment = UserFragment(userId)
                replace(R.id.fragment_container_view, fragment)
                setReorderingAllowed(true)
                addToBackStack(null) // name can be null
            }
        })


        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<UserListFragment>(R.id.fragment_container_view)
            }
        }
    }
}