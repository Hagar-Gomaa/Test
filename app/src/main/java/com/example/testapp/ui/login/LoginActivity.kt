package com.example.testapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.example.testapp.R
import com.example.testapp.databinding.ActivityActivateAccountBinding
import com.example.testapp.databinding.ActivityLoginBinding
import com.example.testapp.ui.bases.BaseActivity
import com.example.testapp.ui.main.MainActivity
import com.example.testapp.ui.register.CommonUiEvent
import com.example.testapp.ui.register.CommonUiState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity :
    BaseActivity<ActivityActivateAccountBinding, CommonUiState, CommonUiEvent>() {
    override val layoutActivityId: Int
        get() = R.layout.activity_login
    override val viewModel: LoginViewModel by viewModels()
    private lateinit var binding: ActivityLoginBinding
    override fun onEvent(event: CommonUiEvent) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, layoutActivityId
        )
        binding.viewModel = viewModel
        checkResult()
        callBacks()
    }

    private fun checkResult() {
        Log.e("LoginActivity", "buttonLogin clicked")
        val intent = Intent(this, MainActivity::class.java)
        var once = true
        lifecycleScope.launch {
            viewModel.state.collectLatest {
                isErrors()
                if (viewModel.state.value.apiSuccess.isNotEmpty() && once) {
                    startActivity(intent)
                    once = false
                }
                val error = viewModel.state.value.isError
                val loading =viewModel.state.value.isLoading
                binding.progress.isVisible = viewModel.state.value.isLoading

                Log.e("error", error.toString())
                Log.e("loading", loading.toString())

            }
        }
    }

    private fun isErrors(): Boolean {
        val apiError = viewModel.state.value.apiError
        return if ((apiError.isNotEmpty() && apiError != "nullnull")) {
            binding.layoutError.isVisible = true
            binding.layoutCotnet.isVisible = false
            val snackbar =
                Snackbar.make(binding.buttonLogin, apiError, Snackbar.LENGTH_SHORT)
            snackbar.show()
            true
        } else {
            false
        }
    }

    fun callBacks() {
        binding.buttonRetry.setOnClickListener {
            binding.layoutCotnet.isVisible = true
            binding.layoutError.isVisible = false

        }
        binding.progress.isVisible = viewModel.state.value.isLoading
    }
}