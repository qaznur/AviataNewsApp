package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.repository.NewsRepository

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    private val job = Job()
    protected val mainScope = CoroutineScope(job + Dispatchers.Main)
    protected val repository = NewsRepository(application)

    fun saveForLateRead(news: News) {
        mainScope.launch {
            news.toReadLater = !news.toReadLater
            repository.controlReadLater(news)
        }
    }

    fun checkForChanges() {
        repository.checkForChanges()
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}