package com.leth.ctg.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leth.ctg.utils.TrainingTag
import java.lang.reflect.Type

@ProvidedTypeConverter
class Converters(private val gson: Gson) {

    @TypeConverter
    fun toTags(string: String): List<TrainingTag> =
        gson.fromJson(string, gsonList<TrainingTag>())

    @TypeConverter
    fun fromTags(value: List<TrainingTag>): String =
        gson.toJson(value, gsonList<TrainingTag>())
}

private inline fun <reified T> gsonList(): Type {
    return object : TypeToken<List<T>>() {}.type
}
