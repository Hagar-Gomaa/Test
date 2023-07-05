package com.example.testapp.ui.register

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.testapp.R
import com.example.testapp.databinding.ActivityRegistrBinding
import com.example.testapp.ui.bases.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegistrBinding, RegisterUiState, RegisterUiEvent>() {
    override val layoutActivityId: Int
        get() = R.layout.activity_registr
    override val viewModel: RegisterViewModel by viewModels()
    lateinit var binding: ActivityRegistrBinding

    private val PICK_IMAGE_REQUEST = 1
    private var imagePath: String = ""
    override fun onEvent(event: RegisterUiEvent) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, layoutActivityId
        )
        callBacks()


    }

    private fun callBacks() {
        binding.imageLocationTextView.setOnClickListener {
            Log.e("RegisterActivity", "buttonSignUp clicked")
            selectImage()
        }
        binding.buttonSignUp.setOnClickListener {

            Log.e("RegisterActivity", "buttonSignUp clicked")
            viewModel.state.value.name = binding.editTextName.text.toString()
            viewModel.state.value.email = binding.editTextIdEmail.text.toString()
            viewModel.state.value.phone = binding.editTextMobileNumber.text.toString()
            viewModel.state.value.city = binding.editTextConfirmCity.text.toString()
            viewModel.state.value.neighborhood_id = binding.editTextNeighboorid.text.toString()



            viewModel.register()
        }
    }

    fun selectImage() {
        // Request permissions
        val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                readPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(readPermission), 1)
        }

        // Launch the image picker
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 2)

    }

    // Handle the result
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 2 && resultCode == RESULT_OK && data != null) {
            val selectedImage = data.data
            val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)

            selectedImage?.let {
                val cursor = contentResolver.query(it, filePathColumn, null, null, null)
                cursor?.moveToFirst()

                val columnIndex = cursor?.getColumnIndex(filePathColumn[0])
                val imagePath = cursor?.getString(columnIndex ?: 0)

                cursor?.close()

                // Save the image path to a variable or database
                // For example, you can save it to a variable like this:
                binding.imageLocationTextView.text = imagePath
                viewModel.state.value.image = imagePath.toString()
            }
        }
    }

}

