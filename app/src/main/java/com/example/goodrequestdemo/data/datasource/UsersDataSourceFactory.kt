package com.example.goodrequestdemo.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.example.goodrequestdemo.reqres.ReqresAPI
import com.example.goodrequestdemo.reqres.User
import io.reactivex.disposables.CompositeDisposable

/**
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class UsersDataSourceFactory(
    private val compositeDisposable: CompositeDisposable,
    private val reqresApi: ReqresAPI
) : DataSource.Factory<Long, User>() {

    val usersDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Long, User> {
        val usersDataSource = UsersDataSource(reqresApi, compositeDisposable)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}
