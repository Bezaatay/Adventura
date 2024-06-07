package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.model.HotelTicketWithFullData
import com.example.reservationproject.R
import com.example.reservationproject.utils.DateFunctions
import com.example.reservationproject.utils.DateFunctions.convertDateToFullDate


class HotelTicketAdapter(
    private val context: Context,
    private var item: List<HotelTicketWithFullData>,
    private var listener: OnBlogItemClickListener
) : RecyclerView.Adapter<HotelTicketAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val username: TextView = itemView.findViewById(R.id.usernameTxt)
        val totalPrice: TextView = itemView.findViewById(R.id.totalPriceTxt)
        val currency2: TextView = itemView.findViewById(R.id.currencyTxt2)
        val currency: TextView = itemView.findViewById(R.id.currencyTxt)
        val checkIn: TextView = itemView.findViewById(R.id.checkInTxt)
        val checkOut: TextView = itemView.findViewById(R.id.checkOutTxt)
        val roomNum: TextView = itemView.findViewById(R.id.roomNumTxt)
        val hotelName: TextView = itemView.findViewById(R.id.hotelNameTxt)
        val locationName: TextView = itemView.findViewById(R.id.locationNameTxt)
        val roomType: TextView = itemView.findViewById(R.id.roomTypeTxt)
        val price: TextView = itemView.findViewById(R.id.priceTxt)
        val roomImg: ImageView = itemView.findViewById(R.id.roomPhoto)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onHotelTicketItemClick(position)
            }
        }
    }

    interface OnBlogItemClickListener {
        fun onHotelTicketItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.hotel_ticket_card_view, parent, false)
        return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].roomInfos.roomImage).into(holder.roomImg)
        holder.username.text = item[position].username
        holder.totalPrice.text = item[position].totalPrice.toString()
        holder.currency.text = "$"
        holder.currency2.text = "$"
        holder.checkIn.text = convertDateToFullDate(item[position].checkInDate)
        holder.checkOut.text = convertDateToFullDate(item[position].checkOutDate)
        holder.roomNum.text =  item[position].roomInfos.roomNumber
        holder.hotelName.text =  item[position].hotelInfos.name
        holder.locationName.text =  item[position].hotelInfos.locationName
        holder.roomType.text =  item[position].roomInfos.roomType
        holder.price.text =  item[position].roomInfos.roomPrice.toString()
        holder.ratingBar.rating = item[position].hotelInfos.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return item.size
    }
}