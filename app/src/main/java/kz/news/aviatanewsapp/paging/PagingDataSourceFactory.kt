package kz.news.aviatanewsapp.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineScope
import kz.news.aviatanewsapp.database.NewsDatabase
import kz.news.aviatanewsapp.domain.News

class PagingDataSourceFactory(private val scope: CoroutineScope,
                              private val source: NEWS_SOURCE,
                              private val database: NewsDatabase) : DataSource.Factory<Int, News>() {

    private val _dataSourceLiveData = MutableLiveData<PageKeyedDataSource<Int, News>>()
    val dataSourceLiveData: LiveData<PageKeyedDataSource<Int, News>>
        get() = _dataSourceLiveData

    override fun create(): DataSource<Int, News> {
        val dataSource = PagingDataSource(scope, source, database)
        _dataSourceLiveData.postValue(dataSource)
        return dataSource
    }

}