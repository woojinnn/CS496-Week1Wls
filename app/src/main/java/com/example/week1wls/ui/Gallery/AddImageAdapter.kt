package com.example.week1wls.ui.Gallery

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.week1wls.R
import kotlinx.android.synthetic.main.item_add_image.view.*
import kotlinx.android.synthetic.main.item_gallery.view.*

class AddImageAdapter(var imageList : ArrayList<AddImageData>) : RecyclerView.Adapter<AddImageAdapter.ViewHolder> () {

    //lateinit var imageList: ArrayList<AddImageData>
    /*
    // for UI test
    var imageList: ArrayList<AddImageData> = arrayListOf(
        AddImageData(R.drawable.cat1),
        AddImageData(R.drawable.cat2),
        AddImageData(R.drawable.cat3),
        AddImageData(R.drawable.cat4)
    )

     */

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            // url 읽어서 넣기
            // Glide.with(itemView).load(item.img).into(itemView.image)
        /*
        // for UI test
        var addimage: ImageView
        init {
            addimage = itemView.findViewById(R.id.addimage)
        }

         */
        fun setItem(item: AddImageData) {
            Glide.with(itemView).load(item.img).into(itemView.addimage)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddImageAdapter.ViewHolder {
        var itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_add_image, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        val item = imageList[i]
        viewHolder.setItem(item)
    }

    override fun getItemCount(): Int {
        return imageList.count()
    }
}