package kz.news.aviatanewsapp.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.database.NewsDatabase
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.network.Network
import kz.news.aviatanewsapp.network.NewsResponse
import kz.news.aviatanewsapp.network.asDatabaseNews
import kz.news.aviatanewsapp.network.asDomainModel

const val PAGE_SIZE = 15

enum class NEWS_SOURCE {
    TOP_HEADLINES, EVERYTHING
}

class PagingDataSource(
    private val scope: CoroutineScope,
    private val source: NEWS_SOURCE,
    private val database: NewsDatabase
) : PageKeyedDataSource<Int, News>() {

    val INITIAL_PAGE = 1

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        scope.launch {
            try {
                Log.d("paging", "loadInitial")
                val response: NewsResponse
                if (source == NEWS_SOURCE.TOP_HEADLINES) {
                    response = Network.newsService.getTopHeadlinesByPage(INITIAL_PAGE, PAGE_SIZE).await()
                } else {
                    response = Network.newsService.getEverythingByPage(INITIAL_PAGE, PAGE_SIZE).await()
//                    database.newsDao.insertMany(*response.asDatabaseNews())
                }

                callback.onResult(response.asDomainModel(), null, INITIAL_PAGE + 1)
            } catch (ex: Exception) {
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        scope.launch {
            try {
                Log.d("paging", "loadAfter")
                val response: NewsResponse = if (source == NEWS_SOURCE.TOP_HEADLINES) {
                    Network.newsService.getTopHeadlinesByPage(params.key, PAGE_SIZE).await()
                } else {
                    Network.newsService.getEverythingByPage(params.key, PAGE_SIZE).await()
                }
                database.newsDao.insertMany(*response.asDatabaseNews())

                callback.onResult(response.asDomainModel(), params.key + 1)
            } catch (ex: Exception) {
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        scope.launch {
            try {
                Log.d("paging", "loadBefore")
                val response: NewsResponse = if (source == NEWS_SOURCE.TOP_HEADLINES) {
                    Network.newsService.getTopHeadlinesByPage(params.key, PAGE_SIZE).await()
                } else {
                    Network.newsService.getEverythingByPage(params.key, PAGE_SIZE).await()
                }
                callback.onResult(response.asDomainModel(), params.key - 1)
            } catch (ex: Exception) {
            }
        }
    }
}