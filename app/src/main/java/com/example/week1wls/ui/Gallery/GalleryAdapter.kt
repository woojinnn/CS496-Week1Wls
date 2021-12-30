package com.example.week1wls.ui.Gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R

class GalleryAdapter : RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    var dataList: ArrayList<GalleryData> = arrayListOf(
        GalleryData(R.drawable.cat1),
        GalleryData(R.drawable.cat2),
        GalleryData(R.drawable.cat3),
        GalleryData(R.drawable.cat4)
    )

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: ImageView

        init {
            image = itemView.findViewById(R.id.image)
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        var view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_gallery, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.image.setImageResource(dataList[i].img)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}