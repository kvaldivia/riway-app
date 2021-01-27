package me.kennyvaldivia.riway.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Flowable

@Dao
interface AlarmDao {
    @Query("select * from alarm")
    fun getAll(): LiveData<List<Alarm?>?>

    @Query("select * from alarm where id in (:ids)")
    fun loadAllByIds(ids: List<Int>): LiveData<List<Alarm?>?>

    @Query( "select * from alarm where id=:id")
    fun get(id: String): LiveData<Alarm?>

    @Query("select * from alarm limit 1")
    fun getUpcomingAlarm(): LiveData<Alarm?>

    @Insert(entity = Alarm::class, onConflict = OnConflictStrategy.ABORT)
    fun create(alarm: Alarm)

    @Insert
    fun insertAll(vararg alarms: Alarm)

    @Update
    fun update(alarm: Alarm): Int
}
