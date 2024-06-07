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
import com.example.bezalibrary.service.model.BlogElement


class BlogAdapter(
    private val context: Context,
    private var item: List<BlogElement>,
    private var listener: OnBlogItemClickListener
) : RecyclerView.Adapter<BlogAdapter.ItemViewHolder>() {

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {

        val imagePhoto: ImageView = itemView.findViewById(R.id.imagePhoto)
        val location: TextView = itemView.findViewById(R.id.location)
        val descriptionTxt: TextView = itemView.findViewById(R.id.descriptionTxt)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val blogDesc = item[position].desc
                val blogImage = item[position].image
                val blogTitle = item[position].title
                listener.onBlogItemClick(position,blogTitle,blogImage,blogDesc)
            }
        }
    }

    interface OnBlogItemClickListener {
        fun onBlogItemClick(position: Int, blogTitle: String, blogImage: String, blogDesc: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ItemViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.blog_card_view, parent, false)
        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        Glide.with(context).load(item[position].image).into(holder.imagePhoto)
        holder.location.text = item[position].title
        val desc = item[position].desc
        val truncatedDesc = if (desc.length > 200) desc.substring(0, 200).substringBeforeLast(" ") + "..." else desc
        holder.descriptionTxt.text = truncatedDesc
    }

    override fun getItemCount(): Int {
        return item.size
    }
}