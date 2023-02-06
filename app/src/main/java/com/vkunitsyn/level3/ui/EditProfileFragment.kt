package com.vkunitsyn.level3.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vkunitsyn.level3.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    lateinit var binding: FragmentEditProfileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentEditProfileBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        processSaveButtonClick()
        processBackButtonClick()
    }

    private fun processSaveButtonClick() {

    }

    private fun processBackButtonClick() {
        binding.ibArrowBackEditProfile.setOnClickListener(){
            parentFragmentManager.popBackStack()
        }
    }
}