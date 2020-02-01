package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import android.os.Handler
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.paging.PageController

class TopHeadlinesViewModel(application: Application) : BaseViewModel(application) {

    private val pageController = PageController()
    private val refreshHandler = Handler()

    val topHeadlines = repository.topHeadlines
    private val refreshTask = object : Runnable {
        override fun run() {
            val task = this
            mainScope.launch {
                loadByPage(true)
                refreshHandler.postDelayed(task, 5000)
            }
        }
    }

    init {
        refreshTask.run()
    }

    fun loadNextIfAvailable() {
        mainScope.launch {
            if (pageController.isLoadAvailable()) {
                loadByPage(false)
            }
        }
    }

    private suspend fun loadByPage(toRefresh: Boolean) {
        pageController.startLoadingPage()
        val pageSize: Int
        if (toRefresh) {
            pageController.refresh()
            pageSize = if (pageController.totalSize == 0) {
                pageController.pageSize
            } else {
                pageController.page * pageController.totalSize
            }
        } else {
            pageSize = pageController.pageSize
        }
        repository.refreshTopHeadlines(toRefresh, pageController.page, pageSize)
        pageController.finishLoadingPage()
        pageController.increasePageAndCheckForMore(pageController.totalSize == topHeadlines.value?.size ?: 0)
        pageController.totalSize = topHeadlines.value?.size ?: 0
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TopHeadlinesViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return TopHeadlinesViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}