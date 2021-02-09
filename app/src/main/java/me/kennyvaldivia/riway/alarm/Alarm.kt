package me.kennyvaldivia.riway.alarm

import android.text.format.DateFormat
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alarm")
data class Alarm(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val alarmId: Long = 0L,
    @ColumnInfo(name = "hour") var hour: Int? = null,
    @ColumnInfo(name = "minute") var minute: Int? = null,
    @ColumnInfo(name = "volume_level") var volumeLvl: Double = 1.0,
    @ColumnInfo(name = "song_uri") var songUri: String? = null,
    @ColumnInfo(name = "label") var label: String? = null,
    @ColumnInfo(name = "is_active") var isActive: Boolean? = true,
    @ColumnInfo(name = "should_vibrate") var shouldVibrate: Boolean? = true,
    @ColumnInfo(name = "monday", defaultValue = "0") var monday: Boolean? = true,
    @ColumnInfo(name = "tuesday", defaultValue = "0") var tuesday: Boolean? = true,
    @ColumnInfo(name = "wednesday", defaultValue = "0") var wednesday: Boolean? = true,
    @ColumnInfo(name = "thursday", defaultValue = "0") var thursday: Boolean? = true,
    @ColumnInfo(name = "friday", defaultValue = "0") var friday: Boolean? = true,
    @ColumnInfo(name = "saturday", defaultValue = "0") var saturday: Boolean? = true,
    @ColumnInfo(name = "sunday", defaultValue = "0") var sunday: Boolean? = true
) {

    enum class DayNames(s: String) {
        MONDAY("monday"),
        TUESDAY("tuesday"),
        WEDNESDAY("wednesday"),
        THURSDAY("thursday"),
        FRIDAY("friday"),
        SATURDAY("saturday"),
        SUNDAY("sunday")
    }

    var time: String?
        get() {
            // TODO: replace with android's date formatting
            if (hour != null && minute != null) {
                val hourString = hour.toString().padStart(2, '0')
                val minuteString = minute.toString().padStart(2, '0')
                return "${hourString}:${minuteString}"
            }
            return "--:--"
        }
        set(value) { return }

    var activeDays: Map<DayNames, Boolean>
        get() {
            return mapOf(
                DayNames.MONDAY to this.monday!!,
                DayNames.TUESDAY to this.tuesday!!,
                DayNames.WEDNESDAY to this.wednesday!!,
                DayNames.THURSDAY to this.thursday!!,
                DayNames.FRIDAY to this.friday!!,
                DayNames.SATURDAY to this.saturday!!,
                DayNames.SUNDAY to this.sunday!!
            )
        }
        set(value: Map<DayNames, Boolean>) {
            value.forEach {
                when (it.key) {
                    DayNames.MONDAY -> monday = it.value
                    DayNames.TUESDAY -> tuesday = it.value
                    DayNames.WEDNESDAY -> wednesday = it.value
                    DayNames.THURSDAY -> thursday = it.value
                    DayNames.FRIDAY -> friday = it.value
                    DayNames.SATURDAY -> saturday = it.value
                    DayNames.SUNDAY -> sunday = it.value
                }
            }
        }
}
