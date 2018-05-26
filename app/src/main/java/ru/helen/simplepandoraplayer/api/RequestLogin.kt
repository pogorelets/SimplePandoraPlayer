package ru.helen.simplepandoraplayer.api

import com.google.gson.annotations.SerializedName
import ru.helen.simplepandoraplayer.repository.Storage

data class RequestLogin(
        val loginType: String = "user",
        val username: String,
        val password: String,
        val includePandoraOneInfo: Boolean = true,
        val includeAdAttributes: Boolean = true,
        val includeSubscriptionExpiration: Boolean = true,
        val includeStationArtUrl: Boolean = true,
        val returnStationList: Boolean = true,
        val returnGenreStations: Boolean = true,
        val partnerAuthToken: String,
        val syncTime: Long = (Storage.syncTime ?: 0L) + (System.currentTimeMillis() / 1000L)

)







