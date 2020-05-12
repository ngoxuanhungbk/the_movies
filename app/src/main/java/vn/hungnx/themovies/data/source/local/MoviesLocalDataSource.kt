package vn.hungnx.themovies.data.source.local

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import vn.hungnx.themovies.data.Movie

class MoviesLocalDataSource (
    private val moviesDao: MoviesDao,
    private val ioDispatcher:CoroutineDispatcher = Dispatchers.IO
){

    fun observerMovie(id:Int):LiveData<Movie?>{
        return moviesDao.findMovie(id)
    }

    fun getLikeMovies():LiveData<List<Movie>>{
        return moviesDao.getMovies()
    }

    suspend fun likeMovie(movie: Movie){
        moviesDao.insert(movie)
    }

    suspend fun unlikeMovie(movie: Movie) = withContext(ioDispatcher){
        moviesDao.deleteMovie(movie.id)
    }
}