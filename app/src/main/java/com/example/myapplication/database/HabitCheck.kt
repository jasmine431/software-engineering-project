package com.example.myapplication.database

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import java.io.Serializable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("habit_id"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["habit_id", "check_time"], unique = true)],
)
data class HabitCheck(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(
        name = "habit_id",
    )
    val habitId: Int,

    @ColumnInfo(
        name = "check_time",
    )
    val checkTime: Long,
) : Serializable


@Entity
data class HabitCheckInsert(
    @ColumnInfo(
        name = "habit_id",
    )
    val habitId: Int,

    @ColumnInfo(
        name = "check_time"
    )
    val checkTime: Long,
)

@Dao
interface HabitCheckDao {
    @Query("SELECT * FROM HabitCheck WHERE habit_id = :habitId ORDER BY check_time")
    fun getHabitCheckList(habitId: Int): Flow<List<HabitCheck>>

    @Query("SELECT * FROM HabitCheck WHERE habit_id = :habitId ORDER BY check_time")
    fun getHabitCheckListSync(habitId: Int): List<HabitCheck>

    @Insert(entity = HabitCheck::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertHabitCheck(habitCheck: HabitCheckInsert): Long
}

class HabitCheckRepository (
    private val habitCheckDao: HabitCheckDao,
) {
    fun getHabitCheckList(habitId: Int) = habitCheckDao.getHabitCheckList(habitId)

    fun getHabitCheckListSync(habitId: Int) = habitCheckDao.getHabitCheckListSync(habitId)

    fun insertHabitCheck(habitCheck: HabitCheckInsert) = habitCheckDao.insertHabitCheck(habitCheck)
}

class HabitCheckViewModel (
    private val repository: HabitCheckRepository,
) : ViewModel() {
    fun getHabitCheckList(habitId: Int) = repository.getHabitCheckList(habitId)

    fun getHabitCheckListSync(habitId: Int) = repository.getHabitCheckListSync(habitId)

    fun insertHabitCheck(habitCheck: HabitCheckInsert) = repository.insertHabitCheck(habitCheck)
}

class HabitCheckViewModelFactory(
    private val repository: HabitCheckRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitCheckViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitCheckViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}