package ru.helen.simplepandoraplayer.ui.station

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_station.*
import ru.helen.simplepandoraplayer.App
import ru.helen.simplepandoraplayer.R
import ru.helen.simplepandoraplayer.model.Station
import ru.helen.simplepandoraplayer.repository.Storage
import ru.helen.simplepandoraplayer.ui.player.PlayerActivity
import ru.helen.simplepandoraplayer.viewmodel.StationModel
import ru.helen.simplepandoraplayer.viewmodel.ViewModelFactory
import javax.inject.Inject

class StationActivity : AppCompatActivity(), StationAdapter.StationListener {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    lateinit var viewModel: StationModel
    var adapter: StationAdapter = StationAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_station)

        App.instance.appComponent.inject(this)
        viewModel = ViewModelProviders
                .of(this, viewModelFactory)
                .get(StationModel::class.java)

        myStations.layoutManager = LinearLayoutManager(this)
        myStations.adapter = adapter
        viewModel.stations.observe(this, Observer { responce -> updateListStations(responce?.stations!!)
        })
        viewModel.getStationList()

    }

    fun updateListStations(stations: List<Station>){
        adapter.swapData(stations)
    }

    override fun onStationClick(station: Station) {
        Storage.currentStation = station
        startActivity(Intent(this, PlayerActivity::class.java ))
    }
}
