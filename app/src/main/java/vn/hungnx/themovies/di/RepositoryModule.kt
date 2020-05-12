package vn.hungnx.themovies.di

import android.content.Context
import androidx.room.Room
import org.koin.core.qualifier.named
import org.koin.dsl.module
import vn.hungnx.themovies.data.source.DefaultMoviesRepository
import vn.hungnx.themovies.data.source.MoviesDataSource
import vn.hungnx.themovies.data.source.MoviesRepository
import vn.hungnx.themovies.data.source.local.MoviesDatabase
import vn.hungnx.themovies.data.source.local.MoviesLocalDataSource
import vn.hungnx.themovies.data.source.remote.ApiService
import vn.hungnx.themovies.data.source.remote.MoviesRemoteDataSource

val repositoryModule = module {
    single(named("database_name")){ provideDatabaseName()}
    single { provideAppDatabase(get(),get(named("database_name"))) }
    single { provideLocalDataSource(get()) }
    single { provideRemoteDataSource(get()) }
    single { provideMoviesRepository(get(), get()) }
}

fun provideDatabaseName() = "Movies.db"

fun provideAppDatabase(context: Context, name:String): MoviesDatabase =
    Room.databaseBuilder(context, MoviesDatabase::class.java, name).build()

fun provideLocalDataSource(moviesDatabase: MoviesDatabase): MoviesLocalDataSource =
    MoviesLocalDataSource(moviesDatabase.moviesDao())

fun provideRemoteDataSource(apiService: ApiService): MoviesDataSource =
    MoviesRemoteDataSource(apiService)

fun provideMoviesRepository(
    localDataSource: MoviesLocalDataSource,
    remoteDataSource: MoviesDataSource
): MoviesRepository =
    DefaultMoviesRepository(remoteDataSource, localDataSource)