package com.example.contact

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.contact.data.Contact

class ContactAdapter(val context: Context, private val clickListener: OnItemClick) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    val allContactList: ArrayList<Contact> = ArrayList()

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactName: TextView = itemView.findViewById(R.id.contactName)
        val contactNo: TextView = itemView.findViewById(R.id.contactNo)
        val deleteContact: ImageView = itemView.findViewById(R.id.deleteContact)
        val callContact: ImageView = itemView.findViewById(R.id.callContact)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.contact_items, null, false)
        val returnView = ContactViewHolder(view)


        returnView.callContact.setOnClickListener {
            clickListener.callContact(allContactList[returnView.adapterPosition])
        }
        returnView.deleteContact.setOnClickListener {
            clickListener.onItemClick(allContactList[returnView.adapterPosition])
        }


        return returnView
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val currentItem = allContactList[position]
        holder.contactName.text = currentItem.name
        holder.contactNo.text = currentItem.phone_no
    }

    override fun getItemCount(): Int {
        return allContactList.size
    }

    fun updateContactList(contactList: List<Contact>) {
        allContactList.clear()
        allContactList.addAll(contactList)

        notifyDataSetChanged()
    }
}

interface OnItemClick {
    fun onItemClick(contact: Contact)

    fun callContact(contact: Contact)
}