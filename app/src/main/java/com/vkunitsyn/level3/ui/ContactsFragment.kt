package com.vkunitsyn.level3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.adapter.ContactsAdapter
import com.vkunitsyn.level3.databinding.FragmentContactsBinding
import com.vkunitsyn.level3.model.Contact
import com.vkunitsyn.level3.utils.FeatureFlags

class ContactsFragment : Fragment(), RecyclerViewInterface {


    private lateinit var viewModel: ContactsViewModel
    private lateinit var binding: FragmentContactsBinding
    lateinit var adapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(View: View, savedInstanceState: Bundle?) {
        super.onViewCreated(View, savedInstanceState)
        setFragmentResultListener("result") { _, bundle ->
            val contact = bundle.getParcelable<Contact>("contact")
            val position = adapter.itemCount
            if (contact != null) {
                addContact(position, contact)
            }
        }
        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)
        viewModel.contactsList.observe(viewLifecycleOwner) { adapter.refresh(it) }
        initAdapter()
        adapter.onTrashBinClick = { position -> deleteContact(position) }
        processBackArrowClick()
        processAddContactClick()
        processContactCardClick()
        enableSwipeToDelete()
    }

    private fun processContactCardClick() {

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
            if (FeatureFlags.transactionsEnabled) {
                val dialogAddContact = AddContactFragment()
                dialogAddContact.show(parentFragmentManager, getString(R.string.tv_add_contact))
            } else {
                findNavController().navigate(R.id.addContactFragment)
            }
        }
    }

    private fun processBackArrowClick() {
        binding.ibArrowBack.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }


    private fun initAdapter() {
        adapter = ContactsAdapter(this)
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

    private fun addContact(position: Int, contact: Contact) {
        viewModel.add(position, contact)
        adapter.notifyItemInserted(position)
    }


    private fun showSnackbar(contact: Contact, position: Int) {
        Snackbar.make(
            binding.rvContacts,
            contact.name + getString(R.string.has_been_removed),
            Snackbar.LENGTH_SHORT
        ).setAction(R.string.undo) {
            addContact(position, contact)
        }.show()
    }

    override fun onItemClick(position: Int) {
        val contact = viewModel.get(position)
        val bundle = Bundle()
        bundle.putParcelable("contact", contact)
        if(FeatureFlags.transactionsEnabled){
            val contactsProfileFragment = ContactsProfileFragment()
            contactsProfileFragment.arguments = bundle
            parentFragmentManager.commit {
                setCustomAnimations(R.anim.fade_in,R.anim.fade_out,R.anim.fade_in,R.anim.fade_out)
                replace(R.id.fragment_container, contactsProfileFragment)
                addToBackStack(null)
            }
        }else{
            findNavController().navigate(R.id.contactsProfileFragment, bundle)
        }
    }
}