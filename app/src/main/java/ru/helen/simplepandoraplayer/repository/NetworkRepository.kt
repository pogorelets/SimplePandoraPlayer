package ru.helen.simplepandoraplayer.repository

import android.arch.lifecycle.MutableLiveData
import ru.helen.simplepandoraplayer.model.AudioItem
import ru.helen.simplepandoraplayer.model.ListStations
import ru.helen.simplepandoraplayer.model.PartnerUser

import ru.helen.simplepandoraplayer.model.User

interface NetworkRepository {
    fun login(partnerId: String, authId: String, login: String, password: String, token: String, user: MutableLiveData<User>, error: MutableLiveData<String>)
    fun partnerLogin(user: MutableLiveData<PartnerUser>,error: MutableLiveData<String>)
    fun getStationList(stations: MutableLiveData<ListStations>,error: MutableLiveData<String>)
    fun getAudioList(audioitems: MutableLiveData<List<AudioItem>>,error: MutableLiveData<String>)
}