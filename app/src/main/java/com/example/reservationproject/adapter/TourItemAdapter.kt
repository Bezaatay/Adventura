package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.reservationproject.R
import com.example.bezalibrary.service.model.TourElement

class TourItemAdapter(
    private val context: Context,
    private var article: List<TourElement>,
    private var listener: OnTourItemClickListener
) : RecyclerView.Adapter<TourItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        OnClickListener {

        val photoImgView: ImageView = itemView.findViewById(R.id.photoImgView)
        val tourNameTxt: TextView = itemView.findViewById(R.id.tourNameTxt)
        val tourTypeTxt: TextView = itemView.findViewById(R.id.tourTypeName)
        val locationTxt: TextView = itemView.findViewById(R.id.locationTxt)
        val currencyTxt: TextView = itemView.findViewById(R.id.currencyTxt)
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val tourId = article[position].id // Item'in ID'sini al
                listener.onTourItemClick(position, tourId) // ID'yi listener'a gönder
            }
        }
    }
    interface OnTourItemClickListener {
        fun onTourItemClick(position: Int, tourId: Long)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.tour_card_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(article[position].image).into(holder.photoImgView)
        holder.tourNameTxt.text = article[position].name
        holder.tourTypeTxt.text = article[position].tourTypeName
        holder.locationTxt.text = article[position].location
        holder.currencyTxt.text = article[position].currencyName
        holder.priceTxt.text = article[position].tourAdultPrice.toString()
        holder.ratingBar.rating = article[position].stars.toFloat()
    }

    override fun getItemCount(): Int {
        return article.size
    }
}