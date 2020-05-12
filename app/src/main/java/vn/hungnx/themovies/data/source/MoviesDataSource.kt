package vn.hungnx.themovies.data.source

import vn.hungnx.themovies.data.Movie
import vn.hungnx.themovies.data.Result
import vn.hungnx.themovies.data.source.remote.response.GetMoviesResponse

interface MoviesDataSource {
    suspend fun getMovies(page:Int):GetMoviesResponse
}