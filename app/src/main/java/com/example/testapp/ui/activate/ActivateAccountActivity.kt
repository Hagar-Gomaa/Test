package com.example.testapp.ui.activate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.app.NotificationCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.testapp.R
import com.example.testapp.databinding.ActivityActivateAccountBinding
import com.example.testapp.ui.bases.BaseActivity
import com.example.testapp.ui.login.LoginActivity
import com.example.testapp.ui.register.CommonUiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CompletableJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.job
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ActivateAccountActivity :
    BaseActivity<ActivityActivateAccountBinding, ActivateUiState, CommonUiEvent>() {
    override val layoutActivityId: Int
        get() = R.layout.activity_activate_account
    override val viewModel: ActivateAccountViewModel by viewModels()
    lateinit var binding: ActivityActivateAccountBinding
    override fun onEvent(event: CommonUiEvent) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, layoutActivityId
        )
        binding.activeCodeViewModel = viewModel
        callBacks()

    }

private var job:Job?=null
    private fun callBacks() {

        binding.buttonActiveCode.setOnClickListener {
            Log.e("ActivateAccountActivity", "buttonActiveCode clicked")

            viewModel.state.value.smsCodeString =binding.editTextCode.text.toString()
            val sms = viewModel.state.value.smsCodeString
            Log.e("ActivateAccountActivity", sms.toString())

            viewModel.activateAccount()
            val intent = Intent(this, LoginActivity::class.java)
            var once = true
    job = lifecycleScope.launch {
        viewModel.state.collectLatest {
            binding.progress.isVisible = viewModel.state.value.isLoading
            viewModel.state.value.smsCodeString
            isErrors()
            if (!viewModel.state.value.apiSuccess.isNullOrEmpty() && once) {
                startActivity(intent)
                once = false
            }

        }
    }
        }
        binding.textResendCode.setOnClickListener {
            Log.e("ActivateAccountActivity", "textResendCode clicked")
           if (job!!.isActive)job!!.cancel()
            viewModel.resendActiveCode()
            lifecycleScope.launch {
                viewModel.state.collectLatest {
                    binding.progress.isVisible = viewModel.state.value.isLoading
                    if (!isErrors()) {
                        val smsCode = viewModel.state.value.smsCode
                        Log.e("sms", smsCode.toString())
                        if ((!smsCode.toString().isNullOrEmpty() && smsCode.toString() != "0")) {
                            showNotification(applicationContext)
                            viewModel.state.value.smsCode = 0
                        }
                    }
                }

            }

        }
        binding.buttonRetry.setOnClickListener {
            binding.layoutCotnet.isVisible = true
            binding.layoutError.isVisible = false
            viewModel.refreshState(0)

        }

    }

    private fun isErrors(): Boolean {
        val apiError = viewModel.state.value.apiError
        return if ((!apiError.isNullOrEmpty() && apiError != "nullnull")) {
            binding.layoutError.isVisible = true
            binding.layoutCotnet.isVisible = false
            val snackbar =
                Snackbar.make(binding.buttonActiveCode, apiError, Snackbar.LENGTH_SHORT)
            snackbar.show()
            viewModel.refreshState(0)
            true
        } else {
            false
        }
    }

    private fun showNotification(
        context: Context

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