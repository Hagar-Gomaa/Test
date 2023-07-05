package com.example.testapp.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.testapp.R
import com.example.testapp.databinding.ActivityMainBinding
import com.example.testapp.ui.bases.BaseActivity
import com.example.testapp.ui.register.RegisterUiEvent
import com.example.testapp.ui.register.RegisterUiState
import com.example.testapp.ui.register.RegisterViewModel

class MainActivity : BaseActivity<ActivityMainBinding,RegisterUiState,RegisterUiEvent>() {
    override val layoutActivityId: Int
        get() = R.layout.activity_main

    //
    //

    override val viewModel:RegisterViewModel by viewModels()
  val binding: ActivityMainBinding
        get() = DataBindingUtil.setContentView(
            this, layoutActivityId
        )


    override fun onEvent(event: RegisterUiEvent) {
        TODO("Not yet implemented")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}