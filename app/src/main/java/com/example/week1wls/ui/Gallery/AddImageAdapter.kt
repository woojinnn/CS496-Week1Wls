package com.example.week1wls.ui.Gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1wls.R
import kotlinx.android.synthetic.main.item_add_image.view.*

class AddImageAdapter(private val context: Context) :
    RecyclerView.Adapter<AddImageAdapter.ViewHolder>() {

    var data = mutableListOf<AddImageData>()
    private var listener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddImageAdapter.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_add_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener {
        fun onItemClick(v: View, tmpdata: AddImageData, pos: Int) {
        }

    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val vimage: ImageView = itemView.addimage
        private val vtag: TextView = itemView.addtag
        private val vlike: TextView = itemView.addLikes
        private val vview: TextView = itemView.addViews

        fun bind(imageItem: AddImageData) {
            Glide.with(context).load(imageItem.imageURL).into(vimage)
            val splitted = imageItem.tags.split(",")
            var tagsStr = ""
            for (elem in splitted) {
                tagsStr += " #${elem.replace(" ", "")} "
            }
            vtag.text = tagsStr
            vlike.text = imageItem.likes.toString()
            vview.text = imageItem.views.toString()

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, imageItem, pos)
                }
            }
        }
    }
}