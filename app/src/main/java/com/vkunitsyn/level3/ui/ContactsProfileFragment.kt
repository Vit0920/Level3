package com.vkunitsyn.level3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.vkunitsyn.level3.databinding.FragmentContactsProfileBinding
import com.vkunitsyn.level3.model.Contact
import com.vkunitsyn.level3.utils.addPictureGlide

class ContactsProfileFragment : Fragment() {
    private lateinit var binding: FragmentContactsProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentContactsProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val contact = arguments?.getParcelable<Contact>("contact")
        binding.apply {
            imgProfilePicture.addPictureGlide(contact?.picture)
            tvName.text = contact?.name
            tvCareer.text = contact?.career
            tvAddress.text = contact?.address
        }
    }
}
