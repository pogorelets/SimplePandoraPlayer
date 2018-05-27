package ru.helen.simplepandoraplayer.ui.result

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_result.*
import ru.helen.simplepandoraplayer.R
import ru.helen.simplepandoraplayer.interfaces.StationListener
import ru.helen.simplepandoraplayer.model.Station
import ru.helen.simplepandoraplayer.repository.Storage
import ru.helen.simplepandoraplayer.ui.player.PlayerActivity
import ru.helen.simplepandoraplayer.ui.station.StationAdapter

class ResultActivity : AppCompatActivity(), StationListener {
    override fun onStationClick(station: Station) {
        Storage.currentStation = station
        startActivity(Intent(this, PlayerActivity::class.java))
    }

    val adapter: StationAdapter = StationAdapter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        searchStations.layoutManager = LinearLayoutManager(this)
        searchStations.adapter = adapter
        adapter.swapData(Storage.searchStations)
    }
}
