package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bezalibrary.service.model.LocationElement
import com.example.reservationproject.R

class LocationSearchBarAdapter(
    private val context: Context,
    private var item: List<LocationElement>,
    private var listener: OnLocationItemClickListener
) : RecyclerView.Adapter<LocationSearchBarAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val locationNameTxt: TextView = itemView.findViewById(R.id.textView12)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val airlineId = item[position].id
                val airlineName = item[position].name
                listener.onLocationNameItemClick(position, airlineId, airlineName)
            }
        }
    }

    interface OnLocationItemClickListener {
        fun onLocationNameItemClick(position: Int, locationId : Long, locationName : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.locationNameTxt.text = item[position].name
    }

    override fun getItemCount(): Int {
        return item.size
    }
}