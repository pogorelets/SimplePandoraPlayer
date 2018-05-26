package ru.helen.simplepandoraplayer.repository

import ru.helen.simplepandoraplayer.model.Station
import ru.helen.simplepandoraplayer.model.User

object Storage {
    lateinit var partnerId: String
    lateinit var authToken: String
    lateinit var partnerAuthToken: String
    lateinit var user: User
    lateinit var currentStation: Station
    var syncTime: Long = 0
    val encryptKey: String = "6#26FRL\$ZWD"
    val decryptKey: String = "R=U!LH\$O2B#"
}