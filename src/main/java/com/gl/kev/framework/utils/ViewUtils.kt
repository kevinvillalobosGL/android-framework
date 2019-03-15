package com.gl.kev.framework.utils

import android.content.Context
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

/**
 * @author <a href="mailto:kevin.villalobos@gorillalogic.com">Kevin Villalobos</a>
 * @since 03/12/2019
 */
class ViewUtils {

    companion object {
        @JvmStatic
        @ColorInt
        fun getColor(context: Context?, @ColorRes colorId: Int): Int {
            return if (context != null) ContextCompat.getColor(context, colorId) else 0
        }
    }
}