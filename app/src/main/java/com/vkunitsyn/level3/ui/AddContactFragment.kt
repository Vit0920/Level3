package com.vkunitsyn.level3.ui

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider.getUriForFile
import androidx.fragment.app.DialogFragment
import com.vkunitsyn.level3.R
import com.vkunitsyn.level3.databinding.FragmentAddContactBinding
import com.vkunitsyn.level3.model.Contact
import com.vkunitsyn.level3.ui.contactsActivity.ContactsActivity
import com.vkunitsyn.level3.utils.Constants
import com.vkunitsyn.level3.utils.addPictureGlide
import java.io.File


class AddContactFragment : DialogFragment() {

    lateinit var binding: FragmentAddContactBinding
    lateinit var getPhoto: ActivityResultLauncher<Uri>
    lateinit var chooseImage: ActivityResultLauncher<String>
    var imageFileUri: Uri? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddContactBinding.inflate(inflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivNewContactPicture.addPictureGlide(R.drawable.default_profile_picture)
        processSaveButtonClick()
        processBackArrowClick()
        processProfilePictureClick()

        getPhoto = registerForActivityResult(ActivityResultContracts.TakePicture())
        { result ->
            if (result) {
                binding.ivNewContactPicture.addPictureGlide(imageFileUri!!)
            } else {
                imageFileUri = null
            }
        }

        chooseImage = registerForActivityResult(ActivityResultContracts.GetContent())
        { result ->
            binding.ivNewContactPicture.addPictureGlide(result!!)
            imageFileUri = result
        }
        processAddPictureClick()
    }

    private fun processProfilePictureClick() {
        binding.ivNewContactPicture.setOnClickListener {
            chooseImage.launch("image/*")
        }
    }


    private fun processAddPictureClick() {

        binding.ibAddPicture.setOnClickListener {
            val image = createImageFile()
            imageFileUri = getUriForFile(requireContext(), Constants.AUTHORITIES, image)
            getPhoto.launch(imageFileUri)
        }
    }

    private fun createImageFile(): File {
        val imagePath = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            Constants.NEW_CONTACT_PROFILE_PICTURE_FILE_NAME,
            Constants.IMAGE_FORMAT,
            imagePath
        )
    }

    private fun processBackArrowClick() {
        binding.ibArrowBackAddContact.setOnClickListener { dismiss() }
    }

    private fun processSaveButtonClick() {

        binding.mbSaveAddContact.setOnClickListener {
            val position = (activity as ContactsActivity).adapter.itemCount
            (activity as ContactsActivity).addContact(position, createNewContact())
            dismiss()
        }
    }

    private fun createNewContact(): Contact {
        val newContact = Contact()
        with(newContact) {
            if (imageFileUri != null) {
                picture = imageFileUri.toString()
            }
            with(binding){
                name = tietUserNameAddContact.text.toString()
                career = tietCareerAddContact.text.toString()
                phone = tietPhoneAddContact.text.toString()
                address = tietAddressAddContact.text.toString()
                birthday = tietBirthdayAddContact.text.toString()
            }
        }
        return newContact
    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }
}


