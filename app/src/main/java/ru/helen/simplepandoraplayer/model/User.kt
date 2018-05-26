package ru.helen.simplepandoraplayer.model

data class User (
        val stationCreationAdUrl: String,
        val hasAudioAds: Boolean,
        val splashScreenAdUrl: String,
        val videoAdUrl: String,
        val username: String,
        val canListen: String,
        val nowPlayingAdUrl: String,
        val userId: String,
        val listeningTimeoutMinutes: String,
        val maxStationsAllowed: Int,
        val listeningTimeoutAlertMsgUri: String,
        val userProfileUrl: String,
        val minimumAdRefreshInterval: Int,
        val userAuthToken: String)