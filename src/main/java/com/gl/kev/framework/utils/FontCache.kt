package com.gl.kev.framework.utils

import android.content.Context
import android.graphics.Typeface
import androidx.collection.ArrayMap

class FontCache {

    companion object {
        private val fontCache = ArrayMap<String, Typeface>()

        operator fun get(fontName: String, context: Context): Typeface? {
            var tf = fontCache[fontName]
            if (tf == null) {
                try {
                    tf = Typeface.createFromAsset(context.assets, "fonts/$fontName")
                } catch (e: Exception) {
                    return null
                }

                fontCache[fontName] = tf
            }
            return tf
        }
    }

}