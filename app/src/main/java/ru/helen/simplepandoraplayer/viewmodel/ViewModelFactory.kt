package ru.helen.simplepandoraplayer.viewmodel

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import ru.helen.simplepandoraplayer.repository.NetworkRepositoryImpl

class ViewModelFactory (val repository: NetworkRepositoryImpl): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginModel::class.java)) {
            return LoginModel(repository) as T
        } else if (modelClass.isAssignableFrom(StationModel::class.java)){
            return StationModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}