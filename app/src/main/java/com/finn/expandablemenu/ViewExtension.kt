package com.finn.expandablemenu

import android.R
import android.content.res.ColorStateList
import android.content.res.Resources
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * Sets the background color for an element given a primary color res
 *
 * @param primaryColorRes
 */
fun View.setBackgroundColorRes(@ColorRes primaryColorRes: Int) {
    val primaryColor =
        ContextCompat.getColor(context, primaryColorRes)
    this.setBackgroundColor(primaryColor)
}

fun Int.toColorState(): ColorStateList {
    return ColorStateList(
        arrayOf(
            intArrayOf(R.attr.state_enabled),
            intArrayOf(-R.attr.state_enabled)
        ), intArrayOf(this, this)
    )
}

fun Int.toDp(): Float = (this * Resources.getSystem().displayMetrics.density)

fun Int.toSp(): Float = (this * Resources.getSystem().displayMetrics.scaledDensity)

fun View.show(){
    this.visibility = View.VISIBLE
}

fun View.hide(){
    this.visibility = View.GONE
}

fun View.invisible(){
    this.visibility = View.INVISIBLE
}
