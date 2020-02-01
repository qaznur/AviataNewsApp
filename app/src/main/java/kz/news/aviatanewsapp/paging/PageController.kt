package kz.news.aviatanewsapp.paging

class PageController {

    val pageSize = 5
    var totalSize = 0
    private var loadInProcess = false
    private var hasMorePage = true
    var page = 1
        private set

    fun refresh() {
        loadInProcess = false
        hasMorePage = true
        page = 1
    }

    fun isLoadAvailable() = hasMorePage && !loadInProcess

    fun startLoadingPage() {
        loadInProcess = true
    }

    fun finishLoadingPage() {
        loadInProcess = false
    }

    fun increasePageAndCheckForMore(isLastPage: Boolean) {
        if (isLastPage) {
            hasMorePage = false
        } else {
            page += 1
        }
    }

}