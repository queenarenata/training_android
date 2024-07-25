package com.example.appsproduct.ui.fragment

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.appsproduct.R
import com.example.appsproduct.data.result.Result
import com.example.appsproduct.data.repository.UserRepository
import com.example.appsproduct.domain.model.Address
import com.example.appsproduct.databinding.FragmentEditProfileBinding
import com.example.appsproduct.data.remote.ApiConfig
import com.example.appsproduct.domain.model.Bank
import com.example.appsproduct.domain.model.Users
import com.example.appsproduct.ui.fragment.FragmentEditProfileArgs
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class FragmentEditProfile : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FragmentEditProfileViewModel
    private val args: FragmentEditProfileArgs by navArgs()
    private lateinit var selectedDate: String
    private var currentSelectedImage: Uri? = null
    private var currentRequestCode: Int = 0
    private val REQUEST_CODE_CAMERA = 101
    private var REQUEST_CODE_GALERY = 102
    private val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.getDefault()).format(
        Date()
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater,container,false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val repository = UserRepository(ApiConfig.instance,requireContext())
        val factory = FragmentEditProfileViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(FragmentEditProfileViewModel::class.java)

        val userId = args.id
        viewModel.showData(userId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.user.collect{
                    result->
                when(result){
                    is Result.Loading ->{
                        binding.pbUser.visibility = View.VISIBLE
                        binding.layoutDetailUser.isVisible = false
                    }
                    is Result.Success ->{
                        setDataUser(result.data)
                        binding.layoutDetailUser.isVisible = true
                        binding.pbUser.visibility = View.GONE
                    }
                    is Result.Failure ->{
                        Toast.makeText(
                            requireContext(),
                            result.exception.message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        binding.btnBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnUpdate.setOnClickListener {
            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.addUser(addData(userId))
                findNavController().navigateUp()
            }
        }

        binding.editTextBirthdate.setOnClickListener {
            showDatePicker()
        }

        binding.btnReset.setOnClickListener{
            showDialogReset(userId)
        }

        binding.btnPickGalery.setOnClickListener {
            currentRequestCode = REQUEST_CODE_GALERY
            requestPermission.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }

        binding.btnPickPhoto.setOnClickListener {
            currentRequestCode = REQUEST_CODE_CAMERA
            requestPermission.launch(Manifest.permission.CAMERA)
        }
    }

    private fun showDialogReset(userId: Int){
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete")
            .setMessage("Anda yakin ingin mereset profile?")
            .setPositiveButton("Yes"){dialog,_->
                viewLifecycleOwner.lifecycleScope.launch {
                    viewModel.resetUser(userId)
                    findNavController().navigateUp()
                    dialog.dismiss()
                }
            }
            .setNegativeButton("Cancel"){dialog,_->
                dialog.dismiss()
            }
            .show()
    }

    private fun showDatePicker(){
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Birth Date")
            .build()
        datePicker.addOnPositiveButtonClickListener {selection->
            val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            selectedDate = sdf.format(Date(selection))
            binding.editTextBirthdate.setText(selectedDate)
        }
        datePicker.show(parentFragmentManager,"BirthDate")
    }

    private fun addData(id: Int): Users {
        return Users(
            id = id,
            address = Address(address = binding.editTextAddress.text.toString()),
            bank = Bank(cardNumber = binding.editTextCreditNumber.text.toString()),
            birthDate = binding.editTextBirthdate.text.toString(),
            bloodGroup = binding.editTextBloodType.text.toString(),
            email = binding.editTextEmail.text.toString(),
            firstName = binding.edtFirstName.text.toString(),
            image = currentSelectedImage.toString(),
            lastName = binding.edtLastName.text.toString(),
            phone = binding.editTextPhoneNumber.text.toString(),
            role = binding.editTextRole.text.toString(),
            university = binding.editTextEducation.text.toString(),
            username = binding.editTextUsername.text.toString()
        )
    }

    private fun setDataUser(users: Users){
        binding.edtFirstName.setText(users.firstName)
        binding.edtLastName.setText(users.lastName)
        binding.editTextEmail.setText(users.email)
        binding.editTextPhoneNumber.setText(users.phone)
        binding.editTextBirthdate.setText(users.birthDate)
        binding.editTextRole.setText(users.role)
        binding.editTextUsername.setText(users.username)
        binding.editTextAddress.setText(users.address.address)
        binding.editTextEducation.setText(users.university)
        binding.editTextBloodType.setText(users.bloodGroup)
        binding.editTextCreditNumber.setText(users.bank.cardNumber)
        Glide.with(requireActivity())
            .load(users.image)
            .placeholder(R.drawable.ic_launcher_background)
            .override(100,100)
            .transform(CenterCrop())
            .into(binding.imgProfile)
        currentSelectedImage = users.image.toUri()
    }

    private var galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val data: Intent? = it.data
                currentSelectedImage = data?.data
                if (currentSelectedImage != null) {
                    binding.imgProfile.setImageURI(currentSelectedImage)
                }

            }
        }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    private var requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (it) {
                when (currentRequestCode) {
                    REQUEST_CODE_CAMERA -> openCamera()
                    REQUEST_CODE_GALERY -> openGallery()
                }
            } else {
                showDialogDenied()
            }
        }

    private fun openCamera() {
        currentSelectedImage = savePhoto(requireContext())
        cameraLauncher.launch(currentSelectedImage)
    }

    private var cameraLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        if (it) {
            binding.imgProfile.setImageURI(currentSelectedImage)
        }
    }

    fun savePhoto(context: Context): Uri {
        var uri: Uri? = null
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "$timeStamp.jpg")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    "Pictures/${resources.getString(R.string.app_name)}"
                )
            }
            uri = context.contentResolver.insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
        }
        return uri!!
    }

    private fun showDialogDenied() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Info")
            .setMessage("App requires permission to work properly. Please grant the required permissions.")
            .setPositiveButton("Ok") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}