package vn.hungnx.themovies.base

import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus

abstract class BaseViewModel : ViewModel(){

    abstract val isLoading:MutableLiveData<Boolean>
    abstract val isError:MutableLiveData<Boolean>
    abstract val errorMessage:MutableLiveData<String>

    protected abstract fun onError(throwable: Throwable)

    private val exceptionHandler = CoroutineExceptionHandler { _ , throwable ->
        viewModelScope.launch {
            onError(throwable)
        }
    }

    val viewModelScopeExceptionHandler = viewModelScope + exceptionHandler
}