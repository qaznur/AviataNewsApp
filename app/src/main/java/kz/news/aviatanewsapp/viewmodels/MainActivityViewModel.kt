package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.database.getInstance
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.paging.PAGE_SIZE
import kz.news.aviatanewsapp.paging.PagingDataSource
import kz.news.aviatanewsapp.paging.PagingDataSourceFactory
import kz.news.aviatanewsapp.repository.MainActivityRepository

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val database = getInstance(application)
    private val repository = MainActivityRepository(database)

    private val pagingDataSource: LiveData<PageKeyedDataSource<Int, News>>
    private val _pagedList: LiveData<PagedList<News>>
    val pagedList: LiveData<PagedList<News>>
        get() = _pagedList

    val topNews = repository.topNews

    init {
        val dataSourceFactory = PagingDataSourceFactory(mainScope, null)
        pagingDataSource = dataSourceFactory.dataSourceLiveData
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        _pagedList = LivePagedListBuilder(dataSourceFactory, config).build()

//        mainScope.launch {
//            repository.refreshNews()
//        }
    }

    fun saveForLateRead(news: News) {
        mainScope.launch {
            news.toReadLater = !news.toReadLater
            repository.updateNews(news)
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainActivityViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
