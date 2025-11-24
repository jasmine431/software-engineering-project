package com.example.myapplication.database

import androidx.annotation.WorkerThread
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.Entity
import androidx.room.Index
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import java.io.Serializable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Entity(
    tableName = "Habit",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["streak"]),
        Index(value = ["last_completed_time"]),
    ],
)
data class Habit(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(
        name = "name",
    )
    val name: String,

    @ColumnInfo(
        name = "frequency",
        defaultValue = "0",
    )
    val frequency: Int,

    @ColumnInfo(
        name = "times_per_frequency",
        defaultValue = "1",
    )
    val timesPerFrequency: Int,

    @ColumnInfo(
        name = "notes",
    )
    val notes: String?,

    @ColumnInfo(
        name = "encouragement",
    )
    val encouragement: String?,


    @ColumnInfo(
        name = "streak",
        defaultValue = "0",
    )
    val streak: Int,

    @ColumnInfo(
        name = "context",
    )
    val context: String?,

    @ColumnInfo(
        name = "last_completed_time",
        defaultValue = "0",
    )
    val lastCompletedTime: Long,

    ) : Serializable


@Entity
data class HabitInsert(
    @ColumnInfo(
        name = "name",
    )
    val name: String,

    @ColumnInfo(
        name = "frequency",
        defaultValue = "0",
    )
    val frequency: Int,

    @ColumnInfo(
        name = "times_per_frequency",
        defaultValue = "1"
    )
    val timesPerFrequency: Int,

    @ColumnInfo(
        name = "notes"
    )
    val notes: String?,


    @ColumnInfo(
        name = "context",
    )
    val context: String?,

    @ColumnInfo(
        name = "encouragement",
    )
    val encouragement: String?,
)


@Entity
data class HabitDataUpdate(
    @ColumnInfo(
        name = "id"
    )
    val id: Int,

    @ColumnInfo(
        name = "name",
    )
    val name: String,

    @ColumnInfo(
        name = "frequency",
        defaultValue = "0",
    )
    val frequency: Int,

    @ColumnInfo(
        name = "times_per_frequency",
        defaultValue = "1",
    )
    val timesPerFrequency: Int,

    @ColumnInfo(
        name = "notes",
    )
    val notes: String?,

    @ColumnInfo(
        name = "context",
    )
    val context: String?,

    @ColumnInfo(
        name = "encouragement",
    )
    val encouragement: String?,
)


@Entity
data class HabitStatusUpdate(
    @ColumnInfo(
        name = "id",
    )
    val id: Int,

    @ColumnInfo(
        name = "streak",
        defaultValue = "0",
    )
    val streak: Int,

    @ColumnInfo(
        name = "last_completed_time",
        defaultValue = "0",
    )
    val lastCompletedTime: Long
)


@Dao
interface HabitDao {
    @Query("SELECT * FROM Habit")
    fun getHabitList(): Flow<List<Habit>>

    @Query("SELECT * FROM Habit")
    fun getHabitListSync(): List<Habit>

    @Query("SELECT * FROM Habit WHERE id = :id")
    fun getHabit(id: Int): Flow<Habit?>

    @Query("SELECT * FROM Habit WHERE id = :id")
    fun getHabitSync(id: Int): Habit?

    @Insert(entity = Habit::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertHabit(habit: HabitInsert): Long

    @Update(entity = Habit::class)
    suspend fun updateHabitData(habitData: HabitDataUpdate)

    @Update(entity = Habit::class)
    suspend fun updateHabitStatus(habitStatus: HabitStatusUpdate)

    @Delete
    suspend fun deleteHabit(habit: Habit)
}

class HabitRepository(
    private val habitDao: HabitDao,
) {
    val getHabitList = habitDao.getHabitList()

    val getHabitListSync = habitDao.getHabitListSync()

    fun getHabit(id: Int) = habitDao.getHabit(id)

    fun getHabitSync(id: Int) = habitDao.getHabitSync(id)

    fun insertHabit(habit: HabitInsert) = habitDao.insertHabit(habit)

    @WorkerThread
    suspend fun updateHabitData(habitData: HabitDataUpdate) = habitDao.updateHabitData(habitData)

    @WorkerThread
    suspend fun updateHabitStatus(habitStatus: HabitStatusUpdate) = habitDao.updateHabitStatus(habitStatus)

    @WorkerThread
    suspend fun delete(habit: Habit) = habitDao.deleteHabit(habit)

}

class HabitViewModel(
    private val repository: HabitRepository,
): ViewModel() {
    val getHabitList = repository.getHabitList

    val getHabitListSync = repository.getHabitListSync

    fun getHabit(id: Int) = repository.getHabit(id)

    fun getHabitSync(id: Int) = repository.getHabitSync(id)

    fun insertHabit(habit: HabitInsert) = repository.insertHabit(habit)

    fun updateHabitData(habitData: HabitDataUpdate) = viewModelScope.launch { repository.updateHabitData(habitData) }

    fun updateHabitStatus(habitStatus: HabitStatusUpdate) = viewModelScope.launch { repository.updateHabitStatus(habitStatus) }

    fun delete(habit: Habit) = viewModelScope.launch { repository.delete(habit) }
}

class HabitViewModelFactory(
    private val repository: HabitRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HabitViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HabitViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}