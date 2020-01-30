package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.database.getInstance
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.repository.MainActivityRepository

class DetailsViewModel(
    application: Application,
    val news: News
) : BaseViewModel(application) {

    class Factory(private val app: Application, private val news: News) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DetailsViewModel(app, news) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
