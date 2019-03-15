package com.gl.kev.framework.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * @author <a href="mailto:kevin.villalobos@gorillalogic.com">Kevin Villalobos</a>
 * @since 03/13/2019
 */
@Suppress("unused")
class GenericListConverter<T> {

    //TODO: test this...
    companion object {

        private val gson = Gson()

        @JvmStatic
        @TypeConverter
        fun <T> listToJson(replyMessages: List<T>?): String? {
            if (replyMessages == null) return null
            val type = object : TypeToken<List<T>>() {}.type
            val json = gson.toJson(replyMessages, type)
            return if (replyMessages.isEmpty()) null else json
        }

        @TypeConverter
        @JvmStatic
        fun <T> jsonToList(json: String): List<T>? {
            val type = object : TypeToken<List<T>>() {}.type
            return gson.fromJson<List<T>>(json, type)
        }
    }

}