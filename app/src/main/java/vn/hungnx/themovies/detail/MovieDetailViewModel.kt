package vn.hungnx.themovies.detail

import androidx.lifecycle.*
import kotlinx.coroutines.launch
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.source.MoviesRepository

class MovieDetailViewModel (private val moviesRepository: MoviesRepository):ViewModel(){
    var isLiked:LiveData<Boolean> = MutableLiveData<Boolean>(false)

    fun start(id:Int){
        isLiked = moviesRepository.observerMovie(id).map {
            it != null
        }
    }

    fun likeMovie(movie:Movie){
        viewModelScope.launch {
            try {
                moviesRepository.likeMovie(movie)
            }catch (e:Exception){
                print("$e")
            }
        }
    }

    fun unlikeMovie(movie:Movie){
        viewModelScope.launch {
            moviesRepository.unlikeMovie(movie)
        }
    }
}