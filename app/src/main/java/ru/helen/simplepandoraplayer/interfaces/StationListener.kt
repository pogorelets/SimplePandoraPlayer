package ru.helen.simplepandoraplayer.interfaces

import ru.helen.simplepandoraplayer.model.Station

interface StationListener {
    fun onStationClick(station: Station)
}