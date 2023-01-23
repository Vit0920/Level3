package com.vkunitsyn.level3.ui.contactsActivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.adapter.ContactsAdapter
import com.vkunitsyn.level3.databinding.ActivityContactsBinding
import com.vkunitsyn.level3.model.Contact
import com.vkunitsyn.level3.ui.AddContactFragment

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    lateinit var adapter: ContactsAdapter

    private val viewModel: ContactsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel.contactsList.observe(this) { adapter.refresh(it) }
        initAdapter()
        adapter.onTrashBinClick = { position -> deleteContact(position) }
        processBackArrowClick()
        processAddContactClick()
        enableSwipeToDelete()
    }


    private fun enableSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                v: RecyclerView,
                h: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(h: RecyclerView.ViewHolder, dir: Int) {
                deleteContact(h.adapterPosition)
            }
        }).attachToRecyclerView(binding.rvContacts)
    }


    private fun processAddContactClick() {
        binding.tvAddContact.setOnClickListener {
            AddContactFragment().show(supportFragmentManager, getString(R.string.tv_add_contact))
        }
    }

    private fun processBackArrowClick() {
        binding.ibArrowBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


    private fun initAdapter() {
        adapter = ContactsAdapter()
        binding.rvContacts.adapter = adapter
    }


    private fun deleteContact(position: Int) {
        val contact = viewModel.get(position)
        viewModel.removeAt(position)
        adapter.notifyItemRemoved(position)
        if (contact != null) {
            showSnackbar(contact, position)
        }
    }

    fun addContact(position: Int, contact: Contact) {
        viewModel.add(position, contact)
        adapter.notifyItemInserted(position)
    }


    private fun showSnackbar(contact: Contact, position: Int) {
        Snackbar.make(
            binding.rvContacts,
            contact.name + getString(R.string.has_been_removed),
            Snackbar.LENGTH_LONG
        ).setAction(R.string.undo) {
            addContact(position, contact)
        }.show()
    }


}



