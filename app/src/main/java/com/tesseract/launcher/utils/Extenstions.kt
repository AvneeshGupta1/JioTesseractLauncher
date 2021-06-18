package com.tesseract.launcher.utils

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tesseract.launcher.R

@BindingAdapter("android:src")
fun ImageView.setImageDrawable(drawable: Drawable?) {
    Glide.with(context).load(drawable).into(this)
}

infix fun View.visibleIf(condition: Boolean) {
    visibility = if (condition) View.VISIBLE else View.GONE
}

fun View.visibleIf(condition: Boolean, otherwise: Int = View.GONE) {
    visibility = if (condition) View.VISIBLE else otherwise
}

infix fun View.goneIf(condition: Boolean) {
    visibility = if (condition) View.GONE else View.VISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}


fun <T : ViewDataBinding> ViewGroup.inflate(layoutId: Int): T {
    return DataBindingUtil.inflate(LayoutInflater.from(context), layoutId, this, false)
}

fun RecyclerView.setDivider() {
    val divider = DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL)
    val drawable = ContextCompat.getDrawable(this.context, R.drawable.recycler_view_divider)
    drawable?.let {
        divider.setDrawable(it)
        addItemDecoration(divider)
    }
}



