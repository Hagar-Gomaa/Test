package com.example.testapp.utils

import android.view.View
import androidx.databinding.BindingAdapter

//
//@BindingAdapter(value = ["app:isVisible"])
//fun View.setHideImageButton(visiable: Boolean?) {
//    this.visibility = if (visiable == true) View.GONE else View.VISIBLE
//}


@BindingAdapter("app:visible")
fun <T> View.visible(visible: Boolean) {
    if (!visible) {
        this.visibility = View.GONE

    } else {
        this.visibility = View.VISIBLE
    }
}