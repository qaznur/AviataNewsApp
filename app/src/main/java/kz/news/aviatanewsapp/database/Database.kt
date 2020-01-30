package kz.news.aviatanewsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DatabaseNews::class], version = 1)
abstract class NewsDatabase : RoomDatabase() {
    abstract val newsDao: NewsDao
}

private lateinit var instance: NewsDatabase

fun getInstance(context: Context): NewsDatabase {
    synchronized(NewsDatabase::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                NewsDatabase::class.java,
                "news.db"
            ).build()
        }
    }
    return instance
}