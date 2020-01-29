package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

open class BaseViewModel(application: Application): AndroidViewModel(application) {

    private val job = Job()
    protected val mainScope = CoroutineScope(job + Dispatchers.Main)

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

}