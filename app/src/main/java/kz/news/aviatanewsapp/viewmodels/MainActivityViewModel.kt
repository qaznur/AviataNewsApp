package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.database.getInstance
import kz.news.aviatanewsapp.repository.MainActivityRepository

class MainActivityViewModel(application: Application) : BaseViewModel(application) {

    private val database = getInstance(application)
    private val repository = MainActivityRepository(database)

    val topNews = repository.topNews

    init {
        mainScope.launch {
            repository.refreshNews()
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
