package kz.news.aviatanewsapp.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.database.NewsDatabase
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.network.Network
import kz.news.aviatanewsapp.network.asDomainModel

const val PAGE_SIZE = 15

class PagingDataSource(
    private val scope: CoroutineScope,
    private val database: NewsDatabase?
) : PageKeyedDataSource<Int, News>() {

    val INITIAL_PAGE = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        scope.launch {
            try {
                Log.d("paging", "loadInitial")
                val response = Network.newsService.getEverythingByPage(INITIAL_PAGE, PAGE_SIZE).await()
//                database.newsDao.insertMany(*response.asDatabaseNews())
                callback.onResult(response.asDomainModel(), null, INITIAL_PAGE + 1)
            } catch (ex: Exception) {
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        scope.launch {
            try {
                Log.d("paging", "loadAfter")
                val response = Network.newsService.getEverythingByPage(params.key, PAGE_SIZE).await()
//                database.newsDao.insertMany(*response.asDatabaseNews())
                callback.onResult(response.asDomainModel(), params.key + 1)
            } catch (ex: Exception) {
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        scope.launch {
            try {
                Log.d("paging", "loadBefore")
                val response = Network.newsService.getEverythingByPage(params.key, PAGE_SIZE).await()
//                database.newsDao.insertMany(*response.asDatabaseNews())
                callback.onResult(response.asDomainModel(), params.key - 1)
            } catch (ex: Exception) {
            }
        }
    }
}