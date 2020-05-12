package vn.hungnx.themovies.home.movies

import android.view.View
import androidx.lifecycle.*
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.launch
import vn.hungnx.themovies.base.BasePagedRefreshViewModel
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.source.MoviesRepository
import vn.hungnx.themovies.data.source.remote.response.BaseListResponse
import vn.hungnx.themovies.util.Event

class MoviesViewModel (private val moviesRepository: MoviesRepository):BasePagedRefreshViewModel<Movie>(){

    lateinit var viewSelected:View
    private val _openMovieEvent = MutableLiveData<Event<Movie>>()
    val openMovieEvent:LiveData<Event<Movie>> = _openMovieEvent
    val favoriteMovies:LiveData<List<Int>> = moviesRepository.favoriteMovies.map {
        it.map { movie->
            movie.id
        }
    }

    override fun onError(throwable: Throwable) {

    }

    override suspend fun loadData(
        loadInitialParams: PageKeyedDataSource.LoadInitialParams<Int>?,
        loadParams: PageKeyedDataSource.LoadParams<Int>?
    ): BaseListResponse<Movie> {
        val page = loadParams?.key?:1
        return moviesRepository.getMovies(page)
    }

    fun openMovieDetail(movie: Movie,view:View){
        viewSelected = view
        _openMovieEvent.value = Event(movie)
    }

    fun toggleFavoriteMovie(movie: Movie){
        viewModelScope.launch {
            if (movie.isFavorite){
                moviesRepository.unlikeMovie(movie)
            }else{
                moviesRepository.likeMovie(movie)
            }
        }
    }
}