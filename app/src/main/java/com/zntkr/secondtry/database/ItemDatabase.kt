package com.zntkr.secondtry.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.zntkr.secondtry.data.Result

// Room database
@Database(
    entities = [Result::class], version = 1
)
abstract class ItemDatabase : RoomDatabase() {
    abstract fun itemDao():ItemDao

    companion object{
        @Volatile
        private var instance : ItemDatabase? = null

        fun getDatabase(context: Context) : ItemDatabase {
            return instance ?: synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    ItemDatabase::class.java,
                    "item_database"
                ).build()
                instance = _instance
                _instance
            }
        }
    }
}

