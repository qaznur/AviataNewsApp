package kz.news.aviatanewsapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import kz.news.aviatanewsapp.domain.News

@Entity
data class DatabaseNews(
    @PrimaryKey
    val id: Long = 0,
    val name: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
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
            content = it.content
        )
    }
}