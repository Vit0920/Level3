package com.vkunitsyn.level3.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.adapter.ContactsAdapter
import com.vkunitsyn.level3.databinding.FragmentContactsBinding
import com.vkunitsyn.level3.model.Contact

class ContactsFragment : Fragment() {

    companion object {
        fun newInstance() = ContactsFragment()
    }

    private lateinit var viewModel: ContactsFragmentViewModel
    private lateinit var binding: FragmentContactsBinding
    lateinit var adapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(View: View, savedInstanceState: Bundle?) {
        super.onViewCreated(View,savedInstanceState)
        viewModel = ViewModelProvider(this).get(ContactsFragmentViewModel::class.java)
        viewModel.contactsList.observe(viewLifecycleOwner) { adapter.refresh(it) }
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
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AddContactFragment())
                .addToBackStack(null)
                .commit()
        }
    }

    private fun processBackArrowClick() {
        binding.ibArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
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