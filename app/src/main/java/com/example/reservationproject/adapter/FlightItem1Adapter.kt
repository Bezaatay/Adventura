package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.model.Flight1Element
import com.example.bezalibrary.service.model.FlightElement
import com.example.reservationproject.R
import com.example.reservationproject.utils.DateFunctions.convertDateTimeToHourAndMinute
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class FlightItem1Adapter(
    private val context: Context,
    private var item: List<Flight1Element>,
    private var listener: OnFlight1ItemClickListener
) : RecyclerView.Adapter<FlightItem1Adapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        OnClickListener {

        val airlinePhoto: ImageView = itemView.findViewById(R.id.airlinePhoto)
        val airlineNameTxt: TextView = itemView.findViewById(R.id.airlineNameTxt)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)
        val duration: TextView = itemView.findViewById(R.id.durationTxt)
        val departureTimeTxt: TextView = itemView.findViewById(R.id.departureTimeTxt)
        val arrivalTimeTxt: TextView = itemView.findViewById(R.id.arrivalTimeTxt)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val flightId = item[position].flightId
                listener.onFlight1ItemClick(position, flightId)
            }
        }
    }
    interface OnFlight1ItemClickListener {
        fun onFlight1ItemClick(position: Int, flightId: Long)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.filtered_flight_card_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].airlineImage).into(holder.airlinePhoto)
        holder.airlineNameTxt.text = item[position].airlineName
        holder.priceTxt.text = item[position].adultSeatPrice.toString()
        holder.duration.text = item[position].duration.toString() + " Saat"
        holder.arrivalTimeTxt.text =convertDateTimeToHourAndMinute(item[position].arrivalTime)
        holder.departureTimeTxt.text =convertDateTimeToHourAndMinute(item[position].departureTime)
    }

    override fun getItemCount(): Int {
        return item.size
    }


}