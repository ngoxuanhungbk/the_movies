package vn.hungnx.themovies.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.source.MoviesRepository

class HomeViewModel(private val moviesRepository: MoviesRepository):ViewModel(){
    val favoriteMovies:LiveData<List<Movie>> = moviesRepository.favoriteMovies
}