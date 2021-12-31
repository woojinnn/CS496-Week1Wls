package com.example.week1wls.ui.contact

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R
import kotlinx.android.synthetic.main.fragment_contact.view.*
import kotlinx.android.synthetic.main.item_contact.*
import kotlinx.android.synthetic.main.item_contact.view.*
import android.content.ContentResolver
import java.lang.Exception


class ContactAdapter(private val list:List<ContactItem>, val context: Context): RecyclerView.Adapter<Holder>() {
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
class Holder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
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

        // popup dialog window
        itemView.root_item_contact.setOnLongClickListener {
            val actionArr: Array<String> = arrayOf("통화", "메시지", "수정", "삭제")
            val alertDialog = AlertDialog.Builder(itemView.context)
                .setTitle("Select Activity")
                .setItems(actionArr,
                    DialogInterface.OnClickListener { dialog, which ->
                        when (actionArr[which]) {
                            "통화" -> {
                                mContactItem?.phone.let { phoneNumber ->
                                val uri = Uri.parse("tel:${phoneNumber.toString()}")
                                val intent = Intent(Intent.ACTION_DIAL, uri)
                                itemView.context.startActivity(intent)}
                                }

                            "메시지" -> {
                                mContactItem?.phone.let { phoneNumber ->
                                    val uri = Uri.parse("smsto:${phoneNumber.toString()}")
                                    val intent = Intent(Intent.ACTION_SENDTO, uri)
                                    itemView.context.startActivity(intent)
                                }
                            }

                            "수정" -> {
                                mContactItem?.id.let { contactId ->
                                    openContactsEdit(contactId.toString())
                                }
//                                ContactFragment.changeList()
                                Toast.makeText(itemView.context, actionArr[which], Toast.LENGTH_LONG).show()
                            }

                            "삭제" -> {
                                mContactItem?.id.let { contactId ->
                                   openContactsRemove(contactId.toString())
                                }
                                Toast.makeText(itemView.context, "Not implemented yet...", Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                .show()
            true
        }
    }

    fun openContactsEdit(contactId:String) {
        var selectedContactUri: Uri? = null
        val phoneUri = ContactsContract.Contacts.CONTENT_URI
        val projections = arrayOf(ContactsContract.Contacts.LOOKUP_KEY, ContactsContract.Contacts._ID)
        val where = ContactsContract.Contacts._ID + "= ?"
        val values = arrayOf<String>(contactId)
        var mCursor: Cursor? = itemView.context.contentResolver.query(phoneUri, projections, where, values, null)

        if( mCursor?.moveToNext() == true) {
            var currentLookupKey = mCursor.getString(0)
            var currentId = mCursor.getLong(1)
            selectedContactUri = ContactsContract.Contacts.getLookupUri(currentId, currentLookupKey)
        }
        val editIntent = Intent(Intent.ACTION_EDIT).apply {
            setDataAndType(selectedContactUri, ContactsContract.Contacts.CONTENT_ITEM_TYPE)
        }
        itemView.context.startActivity(editIntent)
    }

    fun openContactsRemove(contactId:String) {

    }


    fun setProfile(contactItem:ContactItem) {
        this.mContactItem = contactItem
        itemView.textName.text = contactItem.name
        itemView.textPhone.text = contactItem.phone
    }
}