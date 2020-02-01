package kz.news.aviatanewsapp.utils

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.repository.PREF_NAME
import kz.news.aviatanewsapp.repository.PREF_READ_LATER_IDS
import kz.news.aviatanewsapp.repository.PRIVATE_MODE


fun RecyclerView.onScrolledToEnd(layoutManager: LinearLayoutManager, onScrollEnd: () -> Unit) {
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                onScrollEnd()
            }
        }
    })
}

fun toReadLater(context: Context, news: News): Boolean {
    val sharedPreferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
    val gson = Gson()
    val typeString = object : TypeToken<MutableList<String>>() {}.type

    val fromJsonIds = sharedPreferences.getString(PREF_READ_LATER_IDS, null)
    val newsIds = gson.fromJson<MutableList<String>>(fromJsonIds, typeString)
    return !(newsIds == null || !newsIds.contains(news.url))
}