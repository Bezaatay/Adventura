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
import com.example.bezalibrary.service.model.TourTicketWithFullData
import com.example.reservationproject.R
import com.example.reservationproject.utils.DateFunctions


class TourTicketAdapter(
    private val context: Context,
    private var item: List<TourTicketWithFullData>,
    private var listener: OnBlogItemClickListener
) : RecyclerView.Adapter<TourTicketAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val imagePhoto: ImageView = itemView.findViewById(R.id.image)
        val tourName: TextView = itemView.findViewById(R.id.hotelNameTxt)
        val tourType: TextView = itemView.findViewById(R.id.roomTypeTxt)
        val totalPrice: TextView = itemView.findViewById(R.id.totalPriceTxt)
        val currency: TextView = itemView.findViewById(R.id.currencyTxt)
        val currency2: TextView = itemView.findViewById(R.id.currencyTxt2)
        val adultPrice: TextView = itemView.findViewById(R.id.AdultpriceTxt)
        val locationName: TextView = itemView.findViewById(R.id.locationNameTxt)
        val personNum: TextView = itemView.findViewById(R.id.personNumTxt)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onTourTicketItemClick(position)
            }
        }
    }

    interface OnBlogItemClickListener {
        fun onTourTicketItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tour_ticket_card_view, parent, false)
        return ItemViewHolder(view)
    }
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].tourInfo.image).into(holder.imagePhoto)
        holder.tourName.text = item[position].tourName
        holder.tourType.text = " ,"+item[position].tourInfo.tourTypeName
        holder.totalPrice.text =item[position].totalPrice.toString()
        holder.currency.text = "$"
        holder.currency2.text = "$"
        holder.locationName.text = " ,"+item[position].tourInfo.location
        holder.adultPrice.text = item[position].tourInfo.tourAdultPrice.toString()
        holder.personNum.text = item[position].person.toString()
        holder.ratingBar.rating = item[position].tourInfo.rating.toFloat()
    }

    override fun getItemCount(): Int {
        return item.size
    }
}