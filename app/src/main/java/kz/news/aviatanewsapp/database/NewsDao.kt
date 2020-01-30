package kz.news.aviatanewsapp.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(news: DatabaseNews)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMany(vararg news : DatabaseNews)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(news : DatabaseNews): Int

//    @Update(onConflict = OnConflictStrategy.REPLACE)
//    fun updateMany(vararg news : DatabaseNews): Int

    @Query("select * from databasenews")
    fun getTopNews(): LiveData<List<DatabaseNews>>
}