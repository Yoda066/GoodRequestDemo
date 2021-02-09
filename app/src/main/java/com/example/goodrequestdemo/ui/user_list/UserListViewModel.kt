package com.example.goodrequestdemo.ui.user_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.example.goodrequestdemo.data.NetworkState
import com.example.goodrequestdemo.data.datasource.UsersDataSource
import com.example.goodrequestdemo.data.datasource.UsersDataSourceFactory
import com.example.goodrequestdemo.reqres.ReqresAPI
import com.example.goodrequestdemo.reqres.User
import io.reactivex.disposables.CompositeDisposable

class UserListViewModel : ViewModel() {

    var userLiveData: LiveData<PagedList<User>>

    private val compositeDisposable = CompositeDisposable()

    private val pageSize = 5

    private val sourceFactory: UsersDataSourceFactory =
        UsersDataSourceFactory(compositeDisposable, ReqresAPI.INSTANCE)

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        userLiveData = LivePagedListBuilder(sourceFactory, config).build()
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun refresh() {
        sourceFactory.usersDataSourceLiveData.value?.invalidate()
    }

    val loadingState: LiveData<NetworkState> =
        Transformations.switchMap(
            sourceFactory.usersDataSourceLiveData
        ) { it.networkState }
}