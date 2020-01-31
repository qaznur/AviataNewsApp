package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.database.getInstance
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.paging.NEWS_SOURCE
import kz.news.aviatanewsapp.paging.PAGE_SIZE
import kz.news.aviatanewsapp.paging.PagingDataSourceFactory
import kz.news.aviatanewsapp.repository.MainActivityRepository

class EverythingViewModel(application: Application) : BaseViewModel(application) {

    private val database = getInstance(application)
    private val repository = MainActivityRepository(database)

    private val dataSourceFactory: PagingDataSourceFactory
    private val config: PagedList.Config
    private val pagingDataSource: LiveData<PageKeyedDataSource<Int, News>>
    private var _pagedList: LiveData<PagedList<News>>
    val pagedList: LiveData<PagedList<News>>
        get() = _pagedList

    init {
        Log.d("######", " init EverythingViewModel")
        dataSourceFactory = PagingDataSourceFactory(mainScope, NEWS_SOURCE.EVERYTHING, database)
        pagingDataSource = dataSourceFactory.dataSourceLiveData
        config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(PAGE_SIZE)
            .build()
        _pagedList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun saveForLateRead(news: News) {
        mainScope.launch {
            news.toReadLater = !news.toReadLater
            repository.updateNews(news)
        }
    }

    fun onSwipeRefreshed() {
        dataSourceFactory.dataSourceLiveData.value?.invalidate()
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(EverythingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return EverythingViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}