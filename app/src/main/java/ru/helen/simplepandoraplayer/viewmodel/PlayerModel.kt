package ru.helen.simplepandoraplayer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.helen.simplepandoraplayer.model.AudioItem
import ru.helen.simplepandoraplayer.repository.NetworkRepositoryImpl

class PlayerModel(val repository: NetworkRepositoryImpl): ViewModel() {
    var audioitems: MutableLiveData<List<AudioItem>> = MutableLiveData()
    fun getAudio(){
        repository.getAudioList(audioitems)
    }
}