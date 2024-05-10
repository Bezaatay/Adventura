package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reservationproject.R
import com.example.bezalibrary.service.model.FlightElement
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Locale

class FlightItemAdapter(
    private val context: Context,
    private var item: List<FlightElement>,
    private var listener: OnFlightItemClickListener,
    private val callingFragment: String
) : RecyclerView.Adapter<FlightItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        val airlinePhoto: ImageView = itemView.findViewById(R.id.airlinePhoto)
        val airlineNameTxt: TextView = itemView.findViewById(R.id.airlineNameTxt)
        val fromWhereTxt: TextView = itemView.findViewById(R.id.fromWhereTxt)
        val toWhereTxt: TextView = itemView.findViewById(R.id.toWhereTxt)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)
        val flightId: TextView = itemView.findViewById(R.id.flightId)
        val duration: TextView = itemView.findViewById(R.id.durationTxt)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val flightId = item[position].id
                listener.onFlightItemClick(position, flightId)
            }
        }
    }

    interface OnFlightItemClickListener {
        fun onFlightItemClick(position: Int, flightId: Long)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FlightItemAdapter.ItemViewHolder {
        val layout = getLayoutResource(callingFragment)
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ItemViewHolder(view)
    }

    private fun getLayoutResource(fragmentName: String): Int {
        return when (fragmentName) {
            "HomeFragment" -> R.layout.flight_card_view
            "ResFlight" -> R.layout.flight_card_view

            else -> throw IllegalArgumentException("Invalid fragment name provided")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].airlineImage).into(holder.airlinePhoto)
        holder.airlineNameTxt.text = item[position].airlineName
        holder.fromWhereTxt.text = item[position].departureCity
        holder.toWhereTxt.text = item[position].landingCity
        holder.priceTxt.text = item[position].adultSeatPrice.toString()
        holder.flightId.text = item[position].id.toString()
        holder.duration.text = item[position].duration.toString() + " Saat"
        //    holder.arrivalTimeTxt.text =  item[position].arrivalTime //convertDateFormat(item[position].arrivalTime)
        //holder.departureTimeTxt.text =item[position].departureTime// convertDateFormat(item[position].departureTime)
    }

    override fun getItemCount(): Int {
        return item.size
    }
}