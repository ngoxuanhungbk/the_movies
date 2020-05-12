package vn.hungnx.themovies.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import vn.hungnx.themovies.data.Movie

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies")
    fun getMovies():LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Delete
    suspend fun deleteMovie(movie: Movie)

    @Query("DELETE FROM movies WHERE id = :id")
    suspend fun deleteMovie(id:Int)

    @Query("SELECT * FROM movies WHERE id = :id")
    fun findMovie(id: Int):LiveData<Movie?>
}