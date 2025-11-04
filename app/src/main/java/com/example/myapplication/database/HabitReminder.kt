package com.example.myapplication.database

import androidx.annotation.ColorLong
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
import java.time.DayOfWeek
import java.time.LocalTime

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Habit::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("habit_id"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["habit_id", "time", "day"], unique = true)],
)
data class HabitReminder(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(
        name = "habit_id",
    )
    val habitId: Int,

    @ColumnInfo(
        name = "time",
    )
    val time: LocalTime,

    @ColumnInfo(
        name = "day",
    )
    val day: DayOfWeek,
)

@Entity
data class HabitReminderInsert(
    @ColumnInfo(
        name = "habit_id",
    )
    val habitId: Int,

    @ColumnInfo(
        name = "time",
    )
    val time: LocalTime,

    @ColumnInfo(
        name = "day",
    )
    val day: DayOfWeek,
)

@Dao
interface HabitReminderDao{
    @Query("SELECT * FROM HabitReminder WHERE habit_id = :habitId")
    fun getHabitReminderListSync(habitId: Int): List<HabitReminder>

    @Insert(entity = HabitReminder::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertHabitReminder(habitReminder: HabitReminderInsert): Long

    @Query("DELETE FROM HabitReminder WHERE habit_id = :habitId")
    fun deleteHabitReminder(habitId: Int)
}

class HabitReminderRepository(
    private val habitReminderDao: HabitReminderDao,
) {
    fun getHabitReminderListSync(habitId: Int) = habitReminderDao.getHabitReminderListSync(habitId)

    fun insertHabitReminder(habitReminder: HabitReminderInsert) = habitReminderDao.insertHabitReminder(habitReminder)

    fun deleteHabitReminder(habitId: Int) = habitReminderDao.deleteHabitReminder(habitId)
}

class HabitReminderViewModel(
    private val repository: HabitReminderRepository,
) : ViewModel() {
    fun getHabitReminderListSync(habitId: Int) = repository.getHabitReminderListSync(habitId)

    fun insertHabitReminder(habitReminder: HabitReminderInsert) = repository.insertHabitReminder(habitReminder)

    fun deleteHabitReminder(habitId: Int) = repository.deleteHabitReminder(habitId)
}

class HabitReminderViewModelFactory(
    private val repository: HabitReminderRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitReminderViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitReminderViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}