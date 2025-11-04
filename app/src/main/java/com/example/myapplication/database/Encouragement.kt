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
    indices = [Index(value = ["habit_id", "content"], unique = true)],
)
data class Encouragement(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(
        name = "habit_id",
    )
    val habitId: Int,
    @ColumnInfo(
        name = "content",
    )
    val content: String,
) : Serializable

@Entity
data class EncouragementInsert(
    @ColumnInfo(
        name = "habit_id",
    )
    val habitId: Int,
    @ColumnInfo(
        name = "content",
    )
    val content: String,
)

@Dao
interface EncouragementDao {
    @Query("SELECT * FROM Encouragement WHERE habit_id = :habitId")
    fun getEncouragementListSync(habitId: Int): List<Encouragement>

    @Query("SELECT * FROM Encouragement WHERE habit_id = :habitId ORDER BY RANDOM() LIMIT 1")
    fun getRandomEncouragement(habitId: Int): Encouragement?

    @Insert(entity = Encouragement::class, onConflict = OnConflictStrategy.IGNORE)
    fun insertEncouragement(encouragement: EncouragementInsert): Long

    @Query("DELETE FROM Encouragement WHERE habit_id = :habitId")
    fun deleteEncouragement(habitId: Int)
}


class EncouragementRepository(
    private val encouragementDao: EncouragementDao,
) {
    fun getEncouragementListSync(habitId: Int) = encouragementDao.getEncouragementListSync(habitId)

    fun getRandomEncouragement(habitId: Int) = encouragementDao.getRandomEncouragement(habitId)

    fun deleteEncouragement(habitId: Int) = encouragementDao.deleteEncouragement(habitId)

    fun insertEncouragement(encouragement: EncouragementInsert) = encouragementDao.insertEncouragement(encouragement)
}

class EncouragementViewModel(
    private val repository: EncouragementRepository,
) : ViewModel() {
    fun getEncouragementListSync(habitId: Int) = repository.getEncouragementListSync(habitId)

    fun getRandomEncouragement(habitId: Int) = repository.getRandomEncouragement(habitId)

    fun deleteEncouragement(habitId: Int) = repository.deleteEncouragement(habitId)

    fun insertEncouragement(encouragement: EncouragementInsert) = repository.insertEncouragement(encouragement)
}

class EncouragementViewModelFactory(
    private val repository: EncouragementRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EncouragementViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EncouragementViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
