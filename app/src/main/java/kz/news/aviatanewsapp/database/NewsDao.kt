package kz.news.aviatanewsapp.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: DatabaseNews)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(news: List<DatabaseNews>)

    @Query("select * from databasenews")
    fun getTopNews(): LiveData<List<DatabaseNews>>
}