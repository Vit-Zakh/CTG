package com.leth.ctg.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.leth.ctg.data.database.entity.ExerciseEntity
import com.leth.ctg.utils.ExerciseClass
import com.leth.ctg.utils.TrainingType
import java.lang.reflect.Type
import java.util.Currency

@ProvidedTypeConverter
class Converters(private val gson: Gson) {

    @TypeConverter
    fun toTags(string: String): List<TrainingType> =
        gson.fromJson(string, gsonList<TrainingType>())

    @TypeConverter
    fun fromTags(value: List<TrainingType>): String =
        gson.toJson(value, gsonList<TrainingType>())

    @TypeConverter
    fun toExerciseClass(value: String) = enumValueOf<ExerciseClass>(value)

    @TypeConverter
    fun fromExerciseClass(value: ExerciseClass) = value.name

    @TypeConverter
    fun toExerciseEntity(string: String?): ExerciseEntity? = gson.fromJson(string, ExerciseEntity::class.java)

    @TypeConverter
    fun fromExerciseEntity(value: ExerciseEntity?): String? = gson.toJson(value, ExerciseEntity::class.java)

    @TypeConverter
    fun toExerciseEntities(string: String): List<ExerciseEntity> =
        gson.fromJson(string, gsonList<ExerciseEntity>())

    @TypeConverter
    fun fromExerciseEntities(value: List<ExerciseEntity>): String =
        gson.toJson(value, gsonList<ExerciseEntity>())

}

private inline fun <reified T> gsonList(): Type {
    return object : TypeToken<List<T>>() {}.type
}
