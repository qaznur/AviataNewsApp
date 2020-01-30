package kz.news.aviatanewsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.news.aviatanewsapp.domain.News

@Entity
data class DatabaseNews(
    @PrimaryKey
    val url: String,
    val name: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    var toReadLater: Boolean = false
)

fun List<DatabaseNews>.asDomainModel(): List<News> {
    return map {
        News(
            name = it.name,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content,
            toReadLater = it.toReadLater
        )
    }
}