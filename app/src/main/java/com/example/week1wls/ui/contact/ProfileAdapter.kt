package com.example.week1wls.ui.contact

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R
import kotlinx.android.synthetic.main.profile_item_layout.view.*


class ProfileAdapter(private val list:List<Profile>): RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.profile_item_layout, parent, false)
        return Holder(view)
    }
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.setProfile(list[position])
    }
}

@SuppressLint("MissingPermission")
class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mProfile:Profile? = null

    init {
        itemView.btnPhone.setOnClickListener {
            mProfile?.phone.let { phoneNumber ->
                val uri = Uri.parse("tel:${phoneNumber.toString()}")
                val intent = Intent(Intent.ACTION_CALL, uri)
                itemView.context.startActivity(intent)
            }
        }
    }

    fun setProfile(profile:Profile) {
        this.mProfile = profile
        itemView.textName.text = profile.name
        itemView.textPhone.text = profile.phone
    }
}