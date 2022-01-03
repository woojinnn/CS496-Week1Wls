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
import com.example.week1wls.ui.healthcare.HealthAdapter
import kotlinx.android.synthetic.main.item_add_image.view.*
import kotlinx.android.synthetic.main.item_gallery.view.*

class AddImageAdapter(private val context: Context) : RecyclerView.Adapter<AddImageAdapter.ViewHolder> () {

    var data = arrayListOf<AddImageData>()
    private var listener : AddImageAdapter.OnItemClickListener? = null
    //lateinit var imageList: ArrayList<AddImageData>
    //var imageList : ArrayList<AddImageData>
    //lateinit var imageList : ArrayList<AddImageData>
    // for UI test
    /*
    var imageList: ArrayList<AddImageData> = arrayListOf(
        AddImageData(R.drawable.cat1),
        AddImageData(R.drawable.cat2),
        AddImageData(R.drawable.cat3),
        AddImageData(R.drawable.cat4)
    )
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddImageAdapter.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.item_add_image, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*
        val item = imageList[i]
        viewHolder.setItem(items
        */
        //viewHolder.addimage.setImageResource(imageList[i].img)
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    interface OnItemClickListener{
        fun onItemClick(v: View, data: AddImageData, pos: Int)
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val vimage: ImageView = itemView.addimage
        private val vtag: TextView = itemView.addtag

        fun bind(imageItem: AddImageData) {
            vimage.setImageURI(imageItem.img)
            vtag.text = imageItem.tag

            val pos = adapterPosition
            if (pos != RecyclerView.NO_POSITION) {
                itemView.setOnClickListener {
                    listener?.onItemClick(itemView, imageItem, pos)
                }
            }
        }
    }


}