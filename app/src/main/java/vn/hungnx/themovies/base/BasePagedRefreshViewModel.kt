package vn.hungnx.themovies.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import vn.hungnx.themovies.data.source.remote.response.BaseListResponse
import java.lang.Exception

abstract class BasePagedRefreshViewModel<Item> : BaseViewModel() {
    open val firstPage = 1
    open val prefetchDistance = 3
    open val pageSize = 20
    val isRefresh = MutableLiveData(false)
    val isLoadMore = MutableLiveData(false)
    override val isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    override val isError: MutableLiveData<Boolean> = MutableLiveData(false)
    override val errorMessage: MutableLiveData<String> = MutableLiveData()
    private var supervisorJob = SupervisorJob()
    var cancelableScope = viewModelScope + supervisorJob

    private var dataSource: BasePagedKeyDataSource<Item>? = null

    private val pagedListConfig: PagedList.Config by lazy {
        PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(2 * pageSize)
            .setPageSize(pageSize)
            .setPrefetchDistance(prefetchDistance)
            .build()
    }

    val itemList: LiveData<PagedList<Item>> by lazy {
        LivePagedListBuilder(
            object : DataSource.Factory<Int, Item>() {
                override fun create(): DataSource<Int, Item> {
                    return createDataSource()
                }
            }, pagedListConfig
        ).build()
    }

    open fun createDataSource(): BasePagedKeyDataSource<Item> {
        return object : BasePagedKeyDataSource<Item>(viewModel = this) {
            override suspend fun loadDataSource(
                loadInitialParams: LoadInitialParams<Int>?,
                loadParams: LoadParams<Int>?
            ): BaseListResponse<Item> {
                return loadData(loadInitialParams, loadParams)
            }

        }.apply {
            dataSource = this
        }
    }

    open fun onRefresh(){
        supervisorJob.cancel()
        createCancelableScope()
        isRefresh.value = true
        isError.value = false
        dataSource?.invalidate()
    }

    private fun createCancelableScope(){
        supervisorJob = SupervisorJob()
        cancelableScope = viewModelScope + supervisorJob
    }

    abstract suspend fun loadData(
        loadInitialParams: PageKeyedDataSource.LoadInitialParams<Int>?,
        loadParams: PageKeyedDataSource.LoadParams<Int>?
    ): BaseListResponse<Item>
}

abstract class BasePagedKeyDataSource<Item>(private val viewModel: BasePagedRefreshViewModel<Item>) :
    PageKeyedDataSource<Int, Item>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Item>
    ) {
        viewModel.cancelableScope.launch {
            viewModel.isLoading.value = true
            viewModel.isError.value = false
            try {
                val itemList = loadDataSource(loadInitialParams = params)
                callback.onResult(
                    itemList.results!!,
                    null,
                    if (itemList.page!! < itemList.totalPages!!) itemList.page!! + 1 else null
                )
                viewModel.isLoading.value = false
                viewModel.isRefresh.value = false
            } catch (e: Exception) {
                viewModel.isLoading.value = false
                viewModel.isError.value = true
                viewModel.isRefresh.value = false
                viewModel.errorMessage.value = "Error=[$e]"
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {
        viewModel.cancelableScope.launch {
            try {
                val itemList = loadDataSource(loadParams = params)
                callback.onResult(
                    itemList.results!!,
                    if (itemList.page!! < itemList.totalPages!!) itemList.page + 1 else null
                )
            }catch (e:Exception){

            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Item>) {

    }

    abstract suspend fun loadDataSource(
        loadInitialParams: LoadInitialParams<Int>? = null,
        loadParams: LoadParams<Int>? = null
    ): BaseListResponse<Item>
}