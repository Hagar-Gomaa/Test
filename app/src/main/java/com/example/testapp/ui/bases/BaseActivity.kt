package com.example.testapp.ui.bases

import android.os.Bundle
import android.os.PersistableBundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.testapp.BR
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class BaseActivity<VDB : ViewDataBinding, STATE, EVENT> : AppCompatActivity() {

    @get:LayoutRes
    abstract val layoutActivityId: Int
    abstract val viewModel: BaseViewModel<STATE, EVENT>

    private lateinit var _binding: VDB
   // abstract val binding: VDB
     //   get() = _binding

    abstract fun onEvent(event: EVENT)
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
//        _binding = DataBindingUtil.setContentView(
//            this, layoutActivityId
//        )
//        _binding.apply {
//            lifecycleOwner = lifecycleOwner
//            setVariable(BR.viewModel, viewModel)
//        }
        collectLatest { viewModel.event.collectLatest { onEvent(it) } }

    }

    protected fun collectLatest(collect: suspend CoroutineScope.() -> Unit) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                collect()
            }
        }
    }

//    protected fun showSnackBar(messages: String) {
//        Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
//    }

}
