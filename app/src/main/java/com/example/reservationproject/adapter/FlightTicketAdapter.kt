package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.model.FlightTicketWithSecondData
import com.example.reservationproject.R
import com.example.reservationproject.utils.DateFunctions
import com.example.reservationproject.utils.DateFunctions.convertDateTimeToHourAndMinute
import com.example.reservationproject.utils.DateFunctions.convertDateToFullDate


class FlightTicketAdapter(
    private val context: Context,
    private var item: List<FlightTicketWithSecondData>,
    private var listener: OnBlogItemClickListener
) : RecyclerView.Adapter<FlightTicketAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val imagePhoto: ImageView = itemView.findViewById(R.id.airlinePhoto)
        val name: TextView = itemView.findViewById(R.id.nameTxt)
        val surname: TextView = itemView.findViewById(R.id.textView36)
        val mail: TextView = itemView.findViewById(R.id.mailTxt)
        val phone: TextView = itemView.findViewById(R.id.phoneTxt)
        val departureTime: TextView = itemView.findViewById(R.id.departureTimeTxt)
        val arrivalTime: TextView = itemView.findViewById(R.id.arrivalTimeTxt)
        val fullDepartureInfo: TextView = itemView.findViewById(R.id.fullDepartureInfoTxt)
        val fullArrivalInfo: TextView = itemView.findViewById(R.id.fullArrivalInfoTxt)
        val airportInfo: TextView = itemView.findViewById(R.id.airportInfoTxt)
        val seatNum: TextView = itemView.findViewById(R.id.seatNumTxt)
        val duration: TextView = itemView.findViewById(R.id.durationTxt)
        val airlineName: TextView = itemView.findViewById(R.id.airlineNameTxt)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onFlightTicketItemClick(position)
            }
        }
    }

    interface OnBlogItemClickListener {
        fun onFlightTicketItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.flight_ticket_card_view, parent, false)
        return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].secondData.airlineImage).into(holder.imagePhoto)
        holder.name.text = item[position].name
        holder.surname.text = item[position].surname
        holder.mail.text = item[position].email
        holder.phone.text = item[position].phone
        holder.departureTime.text =  convertDateTimeToHourAndMinute(item[position].secondData.departureTime)
        holder.arrivalTime.text =  convertDateTimeToHourAndMinute(item[position].secondData.arrivalTime)
        holder.fullDepartureInfo.text = convertDateToFullDate(item[position].secondData.departureTime) + ", " + item[position].departureCity
        holder.fullArrivalInfo.text = convertDateToFullDate(item[position].secondData.arrivalTime) + ", " + item[position].landingCity
        holder.airportInfo.text = item[position].secondData.airportName
        holder.seatNum.text = "Check-in Yapılmadı!"
        holder.duration.text = item[position].secondData.duration.toString()+" Saat"
        holder.airlineName.text = item[position].secondData.airlineName
    }

    override fun getItemCount(): Int {
        return item.size
    }
}