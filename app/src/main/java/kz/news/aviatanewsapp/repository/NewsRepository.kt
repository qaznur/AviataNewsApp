package kz.news.aviatanewsapp.repository

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.network.Network
import kz.news.aviatanewsapp.network.asDomainModel

const val PRIVATE_MODE = 0
const val PREF_NAME = "news-pref"
const val PREF_READ_LATER = "read_later"
const val PREF_READ_LATER_IDS = "read_later_ids"

class NewsRepository(
    val application: Application
) {

    private var sharedPreferences: SharedPreferences = application.getSharedPreferences(
        PREF_NAME,
        PRIVATE_MODE
    )
    private val gson = Gson()
    private val typeString = object : TypeToken<MutableList<String>>() {}.type
    private val typeList = object : TypeToken<MutableList<News>>() {}.type

    private val _readLaterNews: MutableLiveData<List<News>> = MutableLiveData()
    val readLaterNews: LiveData<List<News>>
        get() = _readLaterNews

    private val _dtoTopHeadlinesList = ArrayList<News>()
    private val _dtoTopHeadlines: MutableLiveData<List<News>> = MutableLiveData()
    val topHeadlines: LiveData<List<News>>
        get() = _dtoTopHeadlines

    private val _dtoEverythingList = ArrayList<News>()
    private val _dtoEverything: MutableLiveData<List<News>> = MutableLiveData()
    val everything: LiveData<List<News>>
        get() = _dtoEverything

    init {
        loadReadLaterNews()
    }

    fun checkForChanges() {
        loadReadLaterNews()
        _dtoTopHeadlines.value = _dtoTopHeadlines.value?.map { top ->
            readLaterNews.value?.map { readLater ->
                top.toReadLater = top.url == readLater.url
            }
            top
        }

        _dtoEverything.value = _dtoEverything.value?.map { top ->
            readLaterNews.value?.map { readLater ->
                top.toReadLater = top.url == readLater.url
            }
            top
        }
    }

    suspend fun refreshTopHeadlines(toRefresh: Boolean, page: Int, pageSize: Int) {
        withContext(Dispatchers.IO) {
            if (toRefresh) {
                _dtoTopHeadlinesList.clear()
            }
            val news = Network.newsService.getTopHeadlinesByPage(page, pageSize).await()
            _dtoTopHeadlinesList.addAll(news.asDomainModel())
            _dtoTopHeadlines.postValue(_dtoTopHeadlinesList)
        }
    }

    suspend fun refreshEverything(toRefresh: Boolean, page: Int, pageSize: Int) {
        withContext(Dispatchers.IO) {
            val news = Network.newsService.getEverythingByPage(page, pageSize).await()
            if (toRefresh) {
                _dtoEverythingList.clear()
            }
            _dtoEverythingList.addAll(news.asDomainModel())
            _dtoEverything.postValue(_dtoEverythingList)
        }
    }

    fun controlReadLater(news: News) {
        val editor = sharedPreferences.edit()

        val fromJsonNews = sharedPreferences.getString(PREF_READ_LATER, null)
        val fromJsonIds = sharedPreferences.getString(PREF_READ_LATER_IDS, null)
        var newsList = gson.fromJson<MutableList<News>>(fromJsonNews, typeList)
        var newsIds = gson.fromJson<MutableList<String>>(fromJsonIds, typeString)

        if (newsList == null) {
            newsList = mutableListOf()
        }
        if (newsIds == null) {
            newsIds = mutableListOf()
        }

        if (newsIds.contains(news.url)) {
            newsList = newsList.filter { it.url != news.url }.toMutableList()
            newsIds.remove(news.url)
        } else {
            newsList.add(news)
            newsIds.add(news.url)
        }

        val toJsonNews = gson.toJson(newsList)
        val toJsonIds = gson.toJson(newsIds)
        editor.putString(PREF_READ_LATER, toJsonNews)
        editor.putString(PREF_READ_LATER_IDS, toJsonIds)
        editor.apply()
    }

    private fun loadReadLaterNews() {
        val fromJsonIds = sharedPreferences.getString(PREF_READ_LATER, null)
        val newsList = gson.fromJson<MutableList<News>>(fromJsonIds, typeList)
        _readLaterNews.value = newsList
    }
}