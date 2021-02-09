package com.example.goodrequestdemo.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.goodrequestdemo.reqres.User

object NavigationManager {

    val loadUserId: LiveData<Event<Long>> = MutableLiveData()

    fun postUserClick(user: User) {
        (loadUserId as MutableLiveData).postValue(Event(user.id))
    }
}