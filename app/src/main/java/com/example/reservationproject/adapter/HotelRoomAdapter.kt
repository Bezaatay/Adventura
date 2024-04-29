package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.model.HotelRoomElement
import com.example.reservationproject.R

class HotelRoomAdapter(
    private val context: Context,
    private var item: List<HotelRoomElement>,
    private var listener: OnHotelRoomItemClickListener
) : RecyclerView.Adapter<HotelRoomAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val roomPhoto: ImageView = itemView.findViewById(R.id.imageView13)
        val roomPrice: TextView = itemView.findViewById(R.id.textView10)
        val roomType: TextView = itemView.findViewById(R.id.textView19)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val roomId = item[position].hotelRoomID // Item'in ID'sini al
                listener.onHotelRoomItemClick(position, roomId) // ID'yi listener'a gönder
            }
        }
    }

    interface OnHotelRoomItemClickListener {
        fun onHotelRoomItemClick(position: Int, roomId: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.hotel_room_card_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].roomImage).into(holder.roomPhoto)
        holder.roomPrice.text = item[position].roomPrice.toString()
        holder.roomType.text = item[position].roomType +" Oda"
    }

    override fun getItemCount(): Int {
        return item.size
    }
}
