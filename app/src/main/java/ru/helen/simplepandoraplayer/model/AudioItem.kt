package ru.helen.simplepandoraplayer.model

data class AudioItem(
        val adToken: String?,
        val songRating: Int?,
        val additionalAudioUrl: List<String>?,
        val audioUrlMap: AudioUrlMap?,
        val trackToken: String?,
        val artistName: String?,
        val albumName: String?,
        val amazonAlbumUrl: String?,
        val songExplorerUrl: String?,
        val albumArtUrl: String?,
        val artistDetailUrl: String?,
        val songName: String
)