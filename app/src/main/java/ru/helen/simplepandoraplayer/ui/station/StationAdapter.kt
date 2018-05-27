package ru.helen.simplepandoraplayer.ui.station

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_station.view.*
import ru.helen.simplepandoraplayer.R
import ru.helen.simplepandoraplayer.interfaces.StationListener
import ru.helen.simplepandoraplayer.model.Station
import java.util.*

class StationAdapter(val listener: StationListener) : RecyclerView.Adapter<StationAdapter.StationHolder>() {
    private var data: List<Station> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationHolder {
        return StationHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_station, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: StationHolder, position: Int) = holder.bind(data[position],listener)

    fun swapData(data: List<Station>) {
        this.data = data
        notifyDataSetChanged()
    }

    class StationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Station,listener: StationListener) = with(itemView) {
            itemView.stationName.text = item.stationName
            setOnClickListener {
                listener.onStationClick(item)
            }
        }
    }
}