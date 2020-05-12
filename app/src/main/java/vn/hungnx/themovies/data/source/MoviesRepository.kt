package vn.hungnx.themovies.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.Result
import vn.hungnx.themovies.data.source.remote.response.GetMoviesResponse

interface MoviesRepository {
    val favoriteMovies:LiveData<List<Movie>>
    fun observerMovie(id:Int):LiveData<Movie?>
    suspend fun likeMovie(movie: Movie)
    suspend fun unlikeMovie(movie: Movie)
    suspend fun getMovies(page:Int):GetMoviesResponse
}