package ru.helen.simplepandoraplayer

import android.app.Application
import android.support.v7.app.AppCompatDelegate
import ru.helen.simplepandoraplayer.di.AppComponent
import ru.helen.simplepandoraplayer.di.AppModule
import ru.helen.simplepandoraplayer.di.DaggerAppComponent

class App: Application() {
    lateinit var appComponent: AppComponent

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        instance = this
        appComponent = DaggerAppComponent
                .builder()
                .appModule(AppModule(applicationContext))
                .build()
    }
}