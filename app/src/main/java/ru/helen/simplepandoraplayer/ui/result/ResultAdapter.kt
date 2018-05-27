package ru.helen.simplepandoraplayer.ui.result

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.helen.simplepandoraplayer.R
import ru.helen.simplepandoraplayer.model.Station
import java.util.*

class ResultAdapter() : RecyclerView.Adapter<ResultAdapter.ResultHolder>() {

    private var data: List<Station> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultHolder {
        return ResultHolder(
                LayoutInflater.from(parent.context)
                        .inflate(R.layout.item_station, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ResultHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<Station>) {
        this.data = data
        notifyDataSetChanged()
    }

    class ResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: Station) = with(itemView) {

            setOnClickListener {

            }
        }
    }
}