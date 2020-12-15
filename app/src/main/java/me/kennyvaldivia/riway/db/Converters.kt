package me.kennyvaldivia.riway.db

import androidx.room.TypeConverter
import java.sql.Time

class Converters {
    @TypeConverter
    fun fromTime(value: Time?): String? {
        return value?.let { it.toString() }
    }

    @TypeConverter
    fun toTime(value: String?): Time? {
        return value?.let { Time.valueOf(it) }
    }
}