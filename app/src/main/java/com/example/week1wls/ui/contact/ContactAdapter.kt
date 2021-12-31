package com.example.week1wls.ui.contact

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R
import kotlinx.android.synthetic.main.fragment_contact.view.*
import kotlinx.android.synthetic.main.item_contact.view.*


class ContactAdapter(private val list:List<ContactItem>): RecyclerView.Adapter<Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)
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
class Holder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
    var mContactItem:ContactItem? = null

    init {
        itemView.btnCall.setOnClickListener {
            mContactItem?.phone.let { phoneNumber ->
                val uri = Uri.parse("tel:${phoneNumber.toString()}")
                val intent = Intent(Intent.ACTION_DIAL, uri)
                itemView.context.startActivity(intent)
            }
        }

        itemView.btnMsg.setOnClickListener {
            mContactItem?.phone.let { phoneNumber ->
                val uri = Uri.parse("smsto:${phoneNumber.toString()}")
                val intent = Intent(Intent.ACTION_SENDTO, uri)
                itemView.context.startActivity(intent)
            }
        }
    }

    fun setProfile(contactItem:ContactItem) {
        this.mContactItem = contactItem
        itemView.textName.text = contactItem.name
        itemView.textPhone.text = contactItem.phone
    }
}