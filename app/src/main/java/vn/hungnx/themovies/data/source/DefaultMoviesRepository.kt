package vn.hungnx.themovies.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.switchMap
import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.source.local.MoviesLocalDataSource
import vn.hungnx.themovies.data.source.remote.response.GetMoviesResponse

class DefaultMoviesRepository(
    private val moviesRemoteDataSource: MoviesDataSource,
    private val moviesLocalDataSource: MoviesLocalDataSource
) :
    MoviesRepository {

    override val favoriteMovies: LiveData<List<Movie>>
        get() = moviesLocalDataSource.getLikeMovies()

    override fun observerMovie(id: Int): LiveData<Movie?> {
        return moviesLocalDataSource.observerMovie(id)
    }

    override suspend fun likeMovie(movie: Movie) {
        moviesLocalDataSource.likeMovie(movie)
    }

    override suspend fun unlikeMovie(movie: Movie) {
        moviesLocalDataSource.unlikeMovie(movie)
    }

    override suspend fun getMovies(page: Int): GetMoviesResponse {
        return moviesRemoteDataSource.getMovies(page)
    }

}