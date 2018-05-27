package ru.helen.simplepandoraplayer.di

import dagger.Component
import ru.helen.simplepandoraplayer.ui.LoginActivity
import ru.helen.simplepandoraplayer.ui.player.PlayerActivity
import ru.helen.simplepandoraplayer.ui.station.StationActivity
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(stationActivity: StationActivity)
    fun inject(playerActivity: PlayerActivity)
}