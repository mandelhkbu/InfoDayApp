package com.example.infoday

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface EventDatabaseDao {
    @Query("SELECT * from event")
    fun getAll(): LiveData<List<Event>>

    @Query("SELECT * from event where deptId = :id")
    fun getByDeptId(id: String): LiveData<List<Event>>

    @Update
    suspend fun update(event:Event)
}

@Database(entities = [Event::class], version = 1)
abstract class EventDatabase : RoomDatabase() {
    abstract fun eventDao(): EventDatabaseDao

    companion object {
        private var INSTANCE: EventDatabase? = null
        fun getInstance(context: Context): EventDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        EventDatabase::class.java,
                        "event_database"
                    )
                        .createFromAsset("events.db")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}