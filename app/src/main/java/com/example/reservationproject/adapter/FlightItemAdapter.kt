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
import com.example.reservationproject.model.FlightElement

class FlightItemAdapter(
    private val context: Context,
    private var item: List<FlightElement>,
    private var listener: OnItemClickListener
) : RecyclerView.Adapter<FlightItemAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val airlinePhoto: ImageView = itemView.findViewById(R.id.airlinePhoto)
        val airlineNameTxt: TextView = itemView.findViewById(R.id.airlineNameTxt)
        val fromWhereTxt: TextView = itemView.findViewById(R.id.fromWhereTxt)
        val toWhereTxt: TextView = itemView.findViewById(R.id.toWhereTxt)
        val priceTxt: TextView = itemView.findViewById(R.id.priceTxt)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.flight_card_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].airlineImage).into(holder.airlinePhoto)
        holder.airlineNameTxt.text = item[position].airlineName
        holder.fromWhereTxt.text = item[position].departureCity
        holder.toWhereTxt.text = item[position].landingCity
        holder.priceTxt.text = item[position].adultSeatPrice.toString()
    }

    override fun getItemCount(): Int {
        return item.size
    }

}