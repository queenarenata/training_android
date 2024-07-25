package com.example.appsproduct.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import com.example.appsproduct.R
import java.io.InputStream

class GalleryIntentFragment : Fragment() {

    private lateinit var imageViewSelected: ImageView
    private lateinit var buttonOpenGallery: Button

    private val selectImageLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val selectedImageUri: Uri? = data?.data
            selectedImageUri?.let { uri ->
                val imageStream: InputStream? = requireContext().contentResolver.openInputStream(uri)
                val selectedImage: Bitmap = BitmapFactory.decodeStream(imageStream)
                imageViewSelected.setImageBitmap(selectedImage)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery_intent, container, false)
        imageViewSelected = view.findViewById(R.id.imageViewSelected)
        buttonOpenGallery = view.findViewById(R.id.buttonOpenGallery)

        buttonOpenGallery.setOnClickListener {
            openGallery()
        }

        return view
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        selectImageLauncher.launch(intent)
    }
}