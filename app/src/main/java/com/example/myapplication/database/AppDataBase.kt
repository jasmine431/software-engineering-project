package com.example.myapplication.database


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import java.util.concurrent.Executors


@Database(
    version = 1,
    entities = [
        Habit::class,
        HabitCheck::class,
        HabitReminder::class,
        Encouragement::class,
    ],
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDataBase : RoomDatabase() {
    abstract fun habitDao(): HabitDao

    abstract fun habitCheckDao(): HabitCheckDao

    abstract fun habitReminderDao(): HabitReminderDao

    abstract fun encouragementDao(): EncouragementDao

    companion object {
        @Volatile
        private var instance: AppDataBase? = null

        fun getDataBase(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val i =
                    Room
                        .databaseBuilder(
                            context.applicationContext,
                            klass = AppDataBase::class.java,
                            name = "habit-maker"
                        ).allowMainThreadQueries()
                        .addMigrations(

                        )
                        .addCallback(
                            object: Callback() {
                                override fun onOpen(db: SupportSQLiteDatabase) {
                                    super.onOpen(db)
                                    Executors.newSingleThreadExecutor().execute {

                                    }

                                }
                            },
                        ).build()
                instance = i
                i
            }
        }
    }
}