package com.example.goodrequestdemo.ui.user_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.goodrequestdemo.databinding.UserListItemBinding
import com.example.goodrequestdemo.reqres.User

class UserListAdapter : PagedListAdapter<User, UserHolder>(UserHolder.UserDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        return UserHolder(
            UserListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: UserHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class UserHolder(
    val binding: UserListItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(user: User?) {
        user?.let {
            binding.user = user
        }
    }

    companion object {
        val UserDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }

}