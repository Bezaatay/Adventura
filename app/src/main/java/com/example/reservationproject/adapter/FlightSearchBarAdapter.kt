package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bezalibrary.service.model.AirportElement
import com.example.reservationproject.R

class FlightSearchBarAdapter(
    private val context: Context,
    private var item: List<AirportElement>,
    private var listener: OnAirlineItemClickListener
) : RecyclerView.Adapter<FlightSearchBarAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val airlineNameTxt: TextView = itemView.findViewById(R.id.textView12)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val airlineId = item[position].id
                val airlineName = item[position].name
                listener.onAirlineItemClick(position, airlineId, airlineName)
            }
        }
    }

    interface OnAirlineItemClickListener {
        fun onAirlineItemClick(position: Int, airlineId : Long, airlineName : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.airlineNameTxt.text = item[position].name
    }

    override fun getItemCount(): Int {
        return item.size
    }

}