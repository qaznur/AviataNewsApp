package kz.news.aviatanewsapp.domain

import kz.news.aviatanewsapp.database.DatabaseNews
import java.io.Serializable

data class News(
    val name: String?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    var toReadLater: Boolean = false
) : Serializable


fun News.asDatabaseNews(): DatabaseNews {
    return DatabaseNews(
        url = url,
        name = name,
        author = author,
        title = title,
        description = description,
        urlToImage = urlToImage,
        publishedAt = publishedAt,
        content = content,
        toReadLater = toReadLater
    )
}