package ru.helen.simplepandoraplayer.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import ru.helen.simplepandoraplayer.model.ListStations
import ru.helen.simplepandoraplayer.repository.NetworkRepositoryImpl

class StationModel(val repository: NetworkRepositoryImpl): ViewModel() {
    var stations: MutableLiveData<ListStations> = MutableLiveData()
    var error: MutableLiveData<String> = MutableLiveData()
    fun getStationList(){
        repository.getStationList(stations,error)
    }

}