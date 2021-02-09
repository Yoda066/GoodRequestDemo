package com.example.goodrequestdemo.data.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import com.example.goodrequestdemo.data.NetworkState
import com.example.goodrequestdemo.reqres.ReqresAPI
import com.example.goodrequestdemo.reqres.User
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

/**
 * Data source for users paging.
 */
class UsersDataSource(
    private val requesApi: ReqresAPI,
    private val compositeDisposable: CompositeDisposable
) : ItemKeyedDataSource<Long, User>() {

    val networkState = MutableLiveData<NetworkState>()

    private var actualPage = 1

    override fun loadInitial(params: LoadInitialParams<Long>, callback: LoadInitialCallback<User>) {
        // update network states.
        // we also provide an initial load state to the listeners so that the UI can know when the
        // very first list is loaded.
        networkState.postValue(NetworkState.LOADING)
        Timber.d("actual page: $actualPage")

        //get the initial users from the api
        compositeDisposable.add(
            requesApi.getUsersForPage(actualPage, params.requestedLoadSize).subscribe({ response ->
                networkState.postValue(NetworkState.LOADED)
                actualPage++
                callback.onResult(response.data)
                Timber.d("actual page: $actualPage")
            }, { throwable ->
                val error = NetworkState.error(throwable.message)
                // publish the error
                networkState.postValue(error)
            })
        )
    }

    override fun loadAfter(params: LoadParams<Long>, callback: LoadCallback<User>) {
        // set network value to loading.
        networkState.postValue(NetworkState.LOADING)

        Timber.d("actual page: $actualPage")

        //get the users from the api after id
        compositeDisposable.add(
            requesApi.getUsersForPage(actualPage, params.requestedLoadSize).subscribe({ response ->
                networkState.postValue(NetworkState.LOADED)
                actualPage++
                callback.onResult(response.data)
            }, { throwable ->
                // publish the error
                networkState.postValue(NetworkState.error(throwable.message))
            })
        )
    }

    override fun invalidate() {
        actualPage = 1
        super.invalidate()
    }

    override fun loadBefore(params: LoadParams<Long>, callback: LoadCallback<User>) {
        // ignored, since we only ever append to our initial load
    }

    override fun getKey(item: User): Long {
        return item.id
    }
}