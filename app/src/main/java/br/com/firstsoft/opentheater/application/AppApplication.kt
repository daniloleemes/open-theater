package br.com.firstsoft.opentheater.application

import android.app.Application
import br.com.firstsoft.opentheater.model.Genre

/**
 * Created by danilolemes on 28/02/2018.
 */
class AppApplication : Application() {

    companion object {
        lateinit var instance: AppApplication
        val BASE_URL = "https://api.themoviedb.org/3/"
        val IMAGE_PATH = "https://image.tmdb.org/t/p/w780"
        val THUMB_PATH = "https://image.tmdb.org/t/p/w300"
        var genres: MutableList<Genre> = mutableListOf()

//        val volumeDao by lazy { AppDatabaseImpl.getInstance().appDatabase.volumeDao() }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}