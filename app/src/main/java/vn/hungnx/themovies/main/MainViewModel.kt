package vn.hungnx.themovies.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.source.MoviesRepository

class MainViewModel (private val repository: MoviesRepository):ViewModel(){
    val favoriteMovies:LiveData<List<Movie>> = repository.favoriteMovies
}