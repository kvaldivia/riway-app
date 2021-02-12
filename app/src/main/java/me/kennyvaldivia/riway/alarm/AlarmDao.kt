package me.kennyvaldivia.riway.alarm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import io.reactivex.Completable
import io.reactivex.Flowable

@Dao
interface AlarmDao {
    @Query("select * from alarm")
    fun getAll(): LiveData<List<Alarm?>?>

    @Query("select * from alarm where id in (:ids)")
    fun loadAllByIds(ids: List<Int>): LiveData<List<Alarm?>?>

    @Query( "select * from alarm where id=:id")
    fun get(id: Long): LiveData<Alarm?>

    @Query("select * from alarm where is_active=1 and is_snoozed=0 limit 1")
    fun getUpcomingAlarm(): LiveData<Alarm?>

    @Insert(entity = Alarm::class, onConflict = OnConflictStrategy.ABORT)
    suspend fun create(alarm: Alarm): Long

    @Insert
    suspend fun insertAll(vararg alarms: Alarm)

    @Update
    suspend fun update(alarm: Alarm): Int
}
