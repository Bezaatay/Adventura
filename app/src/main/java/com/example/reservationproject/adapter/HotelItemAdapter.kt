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
import com.example.reservationproject.R
import com.example.bezalibrary.service.model.HotelElement

class HotelItemAdapter(
    private val context: Context,
    private var article: List<HotelElement>,
    private var listener: OnHotelItemClickListener,
    private val callingFragment: String
) : RecyclerView.Adapter<HotelItemAdapter.ItemViewHolder>(), View.OnClickListener {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photoImgView: ImageView = itemView.findViewById(R.id.photoImgView)
        private val hotelNameTxt: TextView = itemView.findViewById(R.id.hotelNameTxt)
        private val locationTxt: TextView = itemView.findViewById(R.id.locationTxt)
        private val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)

        init {
            itemView.setOnClickListener(this@HotelItemAdapter)
        }

        fun bind(hotelElement: HotelElement) {
            itemView.tag = adapterPosition
            Glide.with(context).load(hotelElement.image).into(photoImgView)
            hotelNameTxt.text = hotelElement.name
            locationTxt.text = hotelElement.locationName
            priceTxt.text = hotelElement.price.toString() + hotelElement.currencyName
            ratingBar.rating = hotelElement.stars.toFloat()
        }
    }

    interface OnHotelItemClickListener {
        fun onHotelItemClick(itemId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layout = getLayoutResource(callingFragment)
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ItemViewHolder(view)
    }

    private fun getLayoutResource(fragmentName: String): Int {
        return when (fragmentName) {
            "HomeFragment" -> R.layout.hotel_card_view
            "SeeAllHotelFragment" -> R.layout.hotel_card_view2
            "SearchHotelFragment" -> R.layout.hotel_card_view2
            else -> throw IllegalArgumentException("Invalid fragment name provided")
        }
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val hotelElement = article[position]
        holder.bind(hotelElement)
    }

    override fun getItemCount(): Int {
        return article.size
    }

    override fun onClick(view: View) {
        val position = view.tag as Int
        val hotelElement = article[position]
        hotelElement.id.let { listener.onHotelItemClick(it) }
    }
}
