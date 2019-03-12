package com.gl.kev.framework.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@Suppress("unused")
@BindingAdapter("font")
fun setFont(textView: TextView, fontName: String) {
    textView.typeface = FontCache[fontName, textView.context]
}

@Suppress("unused")
@BindingAdapter("showIfNull")
fun showIfNull(view: View, o: Any?) {
    view.visibility = if (o == null) View.VISIBLE else View.GONE
}