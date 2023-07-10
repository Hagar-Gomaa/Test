package com.example.testapp.ui.register

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.testapp.R
import com.example.testapp.databinding.ActivityRegistrBinding
import com.example.testapp.ui.activate.ActivateAccountActivity
import com.example.testapp.ui.bases.BaseActivity
import com.example.testapp.ui.login.LoginActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegistrBinding, CommonUiState, CommonUiEvent>() {
    override val layoutActivityId: Int
        get() = R.layout.activity_registr
    override val viewModel: RegisterViewModel by viewModels()
    lateinit var binding: ActivityRegistrBinding
    override fun onEvent(event: CommonUiEvent) {
        when (event) {
            is CommonUiEvent.Go -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, layoutActivityId
        )

        binding.viewModel= viewModel
        callBacks()


    }

    private fun callBacks() {
        val intent = Intent(this, ActivateAccountActivity::class.java)
         var once=true
        binding.imageLocationTextView.setOnClickListener {
            Log.e("RegisterActivity", "SelectImage clicked")
            selectImage()
        }
        binding.buttonSignUp.setOnClickListener {

            Log.e("RegisterActivity", "ButtonSignUp clicked")
            viewModel.register()
            lifecycleScope.launch {
                viewModel.state.collectLatest { state ->
                    binding.progress.isVisible = viewModel.state.value.isLoading

                    if (!viewModel.state.value.apiSuccess.isNullOrEmpty()&&once) {
                        showNotification(applicationContext)
                        startActivity(intent)
                        once=false
                    }
                    val apiError = viewModel.state.value.apiError
                    if (apiError.isNotEmpty() && apiError != "nullnull") {
                        binding.layoutError.isVisible = true
                        binding.layoutCotnet.isVisible = false
                        val snackbar =
                            Snackbar.make(binding.buttonSignUp, apiError, Snackbar.LENGTH_SHORT)
                        snackbar.show()
                    }
                }
            }
        }

        binding.buttonRetry.setOnClickListener {
            binding.layoutCotnet.isVisible = true
            binding.layoutError.isVisible = false
            viewModel.refreshState()

        }
        binding.login.setOnClickListener {
            viewModel.go()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }


    private val PICK_IMAGE_REQUEST = 1
    private fun selectImage() {
        // Request permissions
        val readPermission = Manifest.permission.READ_EXTERNAL_STORAGE
        if (ContextCompat.checkSelfPermission(
                this,
                readPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(this, arrayOf(readPermission), PICK_IMAGE_REQUEST)
        }

        // Launch the image picker
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 2)

    }

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

                binding.imageLocationTextView.text = imagePath
                viewModel.state.value.image = imagePath.toString()
            }
        }
    }
    fun showNotification(context: Context

    ) {
        val intent = Intent(this, ActivateAccountActivity::class.java)
        val channel_id = "notification_channel"

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        // Pass the intent to PendingIntent to start the

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_MUTABLE
        )
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(
            context,
            channel_id
        )
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle("Test App Message")
            .setContentText("your virfication code ")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("your virfication code is " + viewModel.state.value.smsCode)
            )
            .setAutoCancel(true)
            .setVibrate(
                longArrayOf(
                    1000, 1000, 1000,
                    1000, 1000
                )
            )
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager?
        if (Build.VERSION.SDK_INT
            >= Build.VERSION_CODES.O
        ) {
            val notificationChannel = NotificationChannel(
                channel_id, "web_app",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager!!.createNotificationChannel(
                notificationChannel
            )
        }
        notificationManager!!.notify(0, builder.build())
    }


}

