package vn.hungnx.themovies.data.source.remote

import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.Result
import vn.hungnx.themovies.data.source.MoviesDataSource
import vn.hungnx.themovies.data.source.remote.response.GetMoviesResponse

class MoviesRemoteDataSource(private val apiService: ApiService) : MoviesDataSource {
    override suspend fun getMovies(page:Int): GetMoviesResponse {
        return apiService.getMovies(page)
    }

}