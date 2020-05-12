package vn.hungnx.themovies.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import vn.hungnx.themovies.detail.MovieDetailViewModel
import vn.hungnx.themovies.home.HomeViewModel
import vn.hungnx.themovies.home.favorite.FavoriteViewModel
import vn.hungnx.themovies.home.movies.MoviesViewModel
import vn.hungnx.themovies.main.MainViewModel

val viewModelModule = module {
    viewModel { MoviesViewModel(get()) }
    viewModel { MainViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { MovieDetailViewModel(get()) }
    viewModel { HomeViewModel(get()) }
}