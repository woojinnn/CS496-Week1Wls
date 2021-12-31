package com.example.week1wls.ui.Gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1wls.R

class AddImageAdapter(var items: ArrayList<AddImageData>) : RecyclerView.Adapter<AddImageAdapter.ViewHolder> () {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setItem(item: AddImageData) {
            //Glide.with(itemView).load(item.img).into(itemView.img)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddImageAdapter.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.item_gallery, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        var item = items[i]
        //holder.setItem(item)
    }

    override fun getItemCount(): Int {
        return items.count()
    }
}