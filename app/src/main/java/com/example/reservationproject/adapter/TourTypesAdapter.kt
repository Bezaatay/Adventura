package com.example.reservationproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bezalibrary.service.model.TourTypeElement
import com.example.reservationproject.R

class TourTypesAdapter(
    private val context: Context,
    private var item: List<TourTypeElement>,
    private var listener: OnTourTypeItemClickListener
) : RecyclerView.Adapter<TourTypesAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val tourTypeNameTxt: TextView = itemView.findViewById(R.id.textView12)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val tourTypeId = item[position].id
                val tourTypeName = item[position].name
                listener.onTourTypeNameItemClick(position, tourTypeId, tourTypeName)
            }
        }
    }

    interface OnTourTypeItemClickListener {
        fun onTourTypeNameItemClick(position: Int, tourTypeId : Long, tourTypeName : String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_layout, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tourTypeNameTxt.text = item[position].name
    }

    override fun getItemCount(): Int {
        return item.size
    }
}