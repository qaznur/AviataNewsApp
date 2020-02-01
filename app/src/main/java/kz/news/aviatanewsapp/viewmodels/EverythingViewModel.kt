package kz.news.aviatanewsapp.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.paging.PageController

class EverythingViewModel(application: Application) : BaseViewModel(application) {

    private val pageController = PageController()
    val everything = repository.everything

    init {
        mainScope.launch {
            loadByPage(false)
        }
    }

    fun onSwipeRefreshed() {
        mainScope.launch {
            loadByPage(true)
        }
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
        if (toRefresh) {
            pageController.refresh()
        }
        repository.refreshEverything(toRefresh, pageController.page, pageController.pageSize)
        pageController.finishLoadingPage()
        pageController.increasePageAndCheckForMore(pageController.totalSize == everything.value?.size ?: 0)
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