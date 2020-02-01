package kz.news.aviatanewsapp.domain

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kz.news.aviatanewsapp.BR
import java.io.Serializable

class News(
    var _name: String?,
    var _author: String?,
    var _title: String?,
    var _description: String?,
    var _url: String,
    var _urlToImage: String?,
    var _publishedAt: String?,
    var _content: String?,
    var _toReadLater: Boolean = false
) : Serializable, BaseObservable() {

    var name: String?
        @Bindable get() = _name
        set(value) {
            _name = value
            notifyPropertyChanged(BR.name)
        }

    var author: String?
        @Bindable get() = _author
        set(value) {
            _author = value
            notifyPropertyChanged(BR.author)
        }

    var title: String?
        @Bindable get() = _title
        set(value) {
            _title = value
            notifyPropertyChanged(BR.title)
        }

    var description: String?
        @Bindable get() = _description
        set(value) {
            _description = value
            notifyPropertyChanged(BR.description)
        }

    var url: String
        @Bindable get() = _url
        set(value) {
            _url = value
            notifyPropertyChanged(BR.url)
        }

    var urlToImage: String?
        @Bindable get() = _urlToImage
        set(value) {
            _urlToImage = value
            notifyPropertyChanged(BR.urlToImage)
        }

    var publishedAt: String?
        @Bindable get() = _publishedAt
        set(value) {
            _publishedAt = value
            notifyPropertyChanged(BR.publishedAt)
        }

    var content: String?
        @Bindable get() = _content
        set(value) {
            _content = value
            notifyPropertyChanged(BR.content)
        }

    var toReadLater: Boolean
        @Bindable get() = _toReadLater
        set(value) {
            _toReadLater = value
            notifyPropertyChanged(BR.toReadLater)
        }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as News

        if (_name != other._name) return false
        if (_author != other._author) return false
        if (_title != other._title) return false
        if (_description != other._description) return false
        if (_url != other._url) return false
        if (_urlToImage != other._urlToImage) return false
        if (_publishedAt != other._publishedAt) return false
        if (_content != other._content) return false
        if (_toReadLater != other._toReadLater) return false

        return true
    }

    override fun hashCode(): Int {
        var result = _name?.hashCode() ?: 0
        result = 31 * result + (_author?.hashCode() ?: 0)
        result = 31 * result + (_title?.hashCode() ?: 0)
        result = 31 * result + (_description?.hashCode() ?: 0)
        result = 31 * result + _url.hashCode()
        result = 31 * result + (_urlToImage?.hashCode() ?: 0)
        result = 31 * result + (_publishedAt?.hashCode() ?: 0)
        result = 31 * result + (_content?.hashCode() ?: 0)
        result = 31 * result + _toReadLater.hashCode()
        return result
    }


}