package vn.hungnx.themovies.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import vn.hungnx.themovies.data.Movie


@Database(entities = [Movie::class], version = 1, exportSchema = false)
@TypeConverters(MovieConverter::class)
abstract class MoviesDatabase : RoomDatabase(){
    abstract fun moviesDao():MoviesDao
}