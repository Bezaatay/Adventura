package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bezalibrary.service.model.LocationElement
import com.example.bezalibrary.service.model.TourLocation
import com.example.reservationproject.R

class LocationSearchBarTourAdapter(
    private val context: Context,
    private var item: List<TourLocation>,
    private var listener: OnLocationItemClickListener
) : RecyclerView.Adapter<LocationSearchBarTourAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val locationNameTxt: TextView = itemView.findViewById(R.id.textView12)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val airlineName = item[position].location
                listener.onLocationNameItemClick(position, airlineName)
            }
        }
    }

    interface OnLocationItemClickListener {
        fun onLocationNameItemClick(position: Int,locationName : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.locationNameTxt.text = item[position].location
    }

    override fun getItemCount(): Int {
        return item.size
    }
}