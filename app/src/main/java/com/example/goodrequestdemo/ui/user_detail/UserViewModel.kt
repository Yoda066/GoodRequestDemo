package com.example.goodrequestdemo.ui.user_detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.goodrequestdemo.data.NetworkState
import com.example.goodrequestdemo.data.Status
import com.example.goodrequestdemo.reqres.ReqresAPI
import com.example.goodrequestdemo.reqres.User
import com.google.android.material.snackbar.Snackbar
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class UserViewModel : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val loadedUser = MutableLiveData<User?>()
    val loadingState = MutableLiveData<NetworkState>()


    var userId: Long? = null

    fun loadUser(userId: Long) {
        this.userId = userId
        loadingState.postValue(NetworkState.LOADING)

        compositeDisposable.add(
            ReqresAPI.INSTANCE.getUserDetail(userId)
                .subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ response ->
                    loadedUser.postValue(response.data)
                    // clear retry since last request succeeded
                    loadingState.postValue(NetworkState.LOADED)
                }, { throwable ->
                    val error = NetworkState.error(throwable.message)
                    // publish the error
                    loadingState.postValue(error)
                })
        )
    }

    fun reloadUser() {
        userId?.let {
            loadUser(it)
        }
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

}