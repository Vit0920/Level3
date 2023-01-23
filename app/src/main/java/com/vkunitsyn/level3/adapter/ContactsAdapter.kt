package com.vkunitsyn.level3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.databinding.ContactModelLayoutBinding
import com.vkunitsyn.level3.model.Contact
import com.vkunitsyn.level3.utils.addPictureGlide


class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.MyViewHolder>() {

    private lateinit var myRecyclerView: RecyclerView
    var onTrashBinClick: ((Int) -> (Unit))? = null

    private var contactsList: ArrayList<Contact> = ArrayList()


    inner class MyViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        var binding = ContactModelLayoutBinding.bind(item)
        fun bind(contact: Contact) = binding.apply {
            tvModelUserName.text = contact.name
            tvUserModelCareer.text = contact.career
            if (contact.picture.isEmpty()) {
                ivModelProfilePicture.addPictureGlide(R.drawable.default_profile_picture)
            } else {
                ivModelProfilePicture.addPictureGlide(contact.picture)
            }
            btnDelete.setOnClickListener {
                onTrashBinClick?.invoke(adapterPosition)
            }
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        myRecyclerView = recyclerView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.contact_model_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int = contactsList.size


    fun refresh(contacts: ArrayList<Contact>) {
        contactsList = contacts
    }


}