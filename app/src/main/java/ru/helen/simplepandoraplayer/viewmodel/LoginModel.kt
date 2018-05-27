package ru.helen.simplepandoraplayer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.helen.simplepandoraplayer.model.PartnerUser
import ru.helen.simplepandoraplayer.model.User
import ru.helen.simplepandoraplayer.repository.NetworkRepositoryImpl
import ru.helen.simplepandoraplayer.repository.Storage
import ru.helen.simplepandoraplayer.utils.Cript
import ru.helen.simplepandoraplayer.utils.hexStringToByteArray;

class LoginModel(val repository: NetworkRepositoryImpl): ViewModel() {
    var user : MutableLiveData<User> = MutableLiveData()
    var partnerUser : MutableLiveData<PartnerUser> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    fun login(partnerUser: PartnerUser, name: String, password: String) {
        Storage.partnerId = partnerUser.partnerId
        Storage.authToken = partnerUser.partnerAuthToken
        Storage.partnerAuthToken = partnerUser.partnerAuthToken
        Storage.syncTime = decryptSyncTime(partnerUser.syncTime).toLong() - (System.currentTimeMillis() / 1000L)
        repository.login(Storage.partnerId, Storage.authToken,  name,password, Storage.partnerAuthToken, user, error)
    }

    fun partnerLogin() {
        repository.partnerLogin(partnerUser, error)
    }

    private fun decryptSyncTime(raw: String): String {
        val cript = Cript()
        val decoded = raw.hexStringToByteArray()
        var decrypted = cript.decrypt(decoded)
        decrypted = decrypted.copyOfRange(4, decrypted.size)
        return String(decrypted, Charsets.UTF_8)
    }
}