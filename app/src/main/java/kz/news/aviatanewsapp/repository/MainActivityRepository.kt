package kz.news.aviatanewsapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.news.aviatanewsapp.database.NewsDatabase
import kz.news.aviatanewsapp.database.asDomainModel
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.domain.asDatabaseNews
import kz.news.aviatanewsapp.network.Network
import kz.news.aviatanewsapp.network.asDatabaseNews

class MainActivityRepository(private val database: NewsDatabase) {

    val topNews: LiveData<List<News>> = Transformations.map(database.newsDao.getTopNews()) {
        it.asDomainModel()
    }

    suspend fun refreshNews() {
        Log.d("#####", "repository refreshNews")
        withContext(Dispatchers.IO) {
            val news = Network.newsService.getTopHeadlines().await()
            val size = database.newsDao.insertMany(*news.asDatabaseNews())
            Log.d("#####", "insert size: $size")
        }
    }

    suspend fun updateNews(news: News) {
        Log.d("#####", "repository updateNews")
        withContext(Dispatchers.IO) {
            database.newsDao.update(news.asDatabaseNews())
        }
    }

}