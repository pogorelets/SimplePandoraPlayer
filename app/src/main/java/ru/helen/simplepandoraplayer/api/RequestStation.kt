package ru.helen.simplepandoraplayer.api

import ru.helen.simplepandoraplayer.repository.Storage

class RequestStation (
        val userAuthToken: String = Storage.user.userAuthToken,
        val syncTime: Long = (Storage.syncTime ?: 0L) + (System.currentTimeMillis() / 1000L),
        val includeStationArtUrl: Boolean? = true,
        val stationArtSize: String? = null,
        val includeAdAttributes: Boolean? = null,
        val includeStationSeeds: Boolean? = null,
        val includeShuffleInsteadOfQuickMix: Boolean? = null,
        val includeRecommendations: Boolean? = null,
        val includeExplanations: Boolean? = null
)
