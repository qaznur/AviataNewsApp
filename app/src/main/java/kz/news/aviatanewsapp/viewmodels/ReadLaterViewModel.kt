package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kz.news.aviatanewsapp.domain.News

class ReadLaterViewModel(application: Application) : BaseViewModel(application) {

    val readLaterNews: LiveData<List<News>> = repository.readLaterNews

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReadLaterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ReadLaterViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
