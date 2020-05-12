package vn.hungnx.themovies.data.source.remote

import retrofit2.http.GET
import retrofit2.http.Query
import vn.hungnx.themovies.data.source.remote.response.GetMoviesResponse

interface ApiService {
    @GET("3/movie/popular")
    suspend fun getMovies(@Query("page")page:Int = 1):GetMoviesResponse
}