package com.vkunitsyn.level3.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.databinding.FragmentProfileBinding
import com.vkunitsyn.level3.utils.Constants


class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvName.text = arguments?.getString(Constants.USER_NAME)
        processViewContactsButtonClick()
    }

    private fun processViewContactsButtonClick() {
        binding.mbViewContacts.setOnClickListener(){
            parentFragmentManager.commit {
                setCustomAnimations(R.anim.fade_in,R.anim.fade_out)
                replace<ContactsFragment>(R.id.fragment_container)
                addToBackStack(null)
            }
        }
    }
}