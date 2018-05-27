package ru.helen.simplepandoraplayer.api

import ru.helen.simplepandoraplayer.repository.Storage

data class RequestAudio (
    val userAuthToken: String = Storage.user.userAuthToken,
    val additionalAudioUrl: String = "HTTP_32_AACPLUS_ADTS,HTTP_64_AACPLUS_ADTS",
    val syncTime: Long = (Storage.syncTime ?: 0L) + (System.currentTimeMillis() / 1000L),
    val stationToken: String? = Storage.currentStation.stationToken
)