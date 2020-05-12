package vn.hungnx.themovies.home.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.source.MoviesRepository
import vn.hungnx.themovies.util.Event

class FavoriteViewModel (private val moviesRepository: MoviesRepository):ViewModel(){
    val favoriteMovies:LiveData<List<Movie>> = moviesRepository.favoriteMovies

    private val _openMovieEvent = MutableLiveData<Event<Movie>>()
    val openMovieEvent:LiveData<Event<Movie>> = _openMovieEvent

    fun openMovieDetail(movie: Movie){
        _openMovieEvent.value = Event(movie)
    }
}