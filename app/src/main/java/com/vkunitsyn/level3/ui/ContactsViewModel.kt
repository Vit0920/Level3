package com.vkunitsyn.level3.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vkunitsyn.level3.model.Contact
import com.vkunitsyn.level3.utils.ContactsData

class ContactsViewModel : ViewModel() {

    val contactsList: MutableLiveData<ArrayList<Contact>> by lazy {
        MutableLiveData<ArrayList<Contact>>()
    }

    init {
        contactsList.value = ContactsData.getData()
    }

    fun add(position: Int, contact: Contact){
        contactsList.value?.add(position,contact)
    }

    fun remove (contact: Contact){
        contactsList.value?.remove(contact)
    }

    fun removeAt(position: Int){
        contactsList.value?.removeAt(position)
    }

    fun get (position: Int): Contact? = contactsList.value?.get(position)


    fun getSize(): Int? = contactsList.value?.size
}