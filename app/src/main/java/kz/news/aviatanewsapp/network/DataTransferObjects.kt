package kz.news.aviatanewsapp.network

import kz.news.aviatanewsapp.domain.News

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

fun NewsResponse.asDomainModel(): List<News> {
    return articles.map {
        News(
            _name = it.source?.name,
            _author = it.author,
            _title = it.title,
            _description = it.description,
            _url = it.url,
            _urlToImage = it.urlToImage,
            _publishedAt = it.publishedAt,
            _content = it.content
        )
    }
}