package vn.hungnx.themovies

import android.app.Application
import androidx.multidex.MultiDex
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import vn.hungnx.themovies.di.appModules

class MoviesApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        startKoin {
            androidContext(this@MoviesApplication)
            modules(appModules)
        }
    }
}