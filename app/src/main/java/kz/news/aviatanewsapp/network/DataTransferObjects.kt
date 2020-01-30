package kz.news.aviatanewsapp.network

import kz.news.aviatanewsapp.database.DatabaseNews

data class NewsResponse(
    val status: String?,
    val totalResults: Int?,
    val articles: List<Article>
)

data class Article(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

data class Source(
    val id: String?,
    val name: String?
)

fun NewsResponse.asDatabaseNews(): Array<DatabaseNews> {
    return articles.map {
        DatabaseNews(
            name = it.source?.name,
            author = it.author,
            title = it.title,
            description = it.description,
            url = it.url,
            urlToImage = it.urlToImage,
            publishedAt = it.publishedAt,
            content = it.content
        )
    }.toTypedArray()
}