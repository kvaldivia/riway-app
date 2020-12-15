package me.kennyvaldivia.riway.alarm

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

enum class DayNames(s: String) {
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday")
}

@Entity(tableName = "alarm")
data class Alarm (
    @PrimaryKey @ColumnInfo(name = "id") val alarmId: Int,
    @ColumnInfo(name = "hour") var hour: Int,
    @ColumnInfo(name = "minute") var minute: Int,
    @ColumnInfo(name = "volume_level") var volumeLvl: Int,
    @ColumnInfo(name = "song_uri") var songUri: String,
    @ColumnInfo(name = "label") var label: String,
    @ColumnInfo(name = "is_active") var isActive: Boolean,
    @ColumnInfo(name = "should_vibrate") var shouldVibrate: Boolean,
    @ColumnInfo(name = "monday") var monday: Boolean,
    @ColumnInfo(name = "tuesday") var tuesday: Boolean,
    @ColumnInfo(name = "wednesday") var wednesday: Boolean,
    @ColumnInfo(name = "thursday") var thursday: Boolean,
    @ColumnInfo(name = "friday") var friday: Boolean,
    @ColumnInfo(name = "saturday") var saturday: Boolean,
    @ColumnInfo(name = "sunday") var sunday: Boolean
) {

    fun activeDays(): Map<DayNames, Boolean> {
        return mapOf(
            DayNames.MONDAY to this.monday,
            DayNames.TUESDAY to this.tuesday,
            DayNames.WEDNESDAY to this.wednesday,
            DayNames.THURSDAY to this.thursday,
            DayNames.FRIDAY to this.friday,
            DayNames.SATURDAY to this.saturday,
            DayNames.SUNDAY to this.sunday
        )
    }
}
