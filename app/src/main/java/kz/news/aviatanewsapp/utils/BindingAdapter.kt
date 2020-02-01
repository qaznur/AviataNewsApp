package kz.news.aviatanewsapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.domain.News


@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    url?.let {
        Glide.with(imageView.context)
            .load(url)
            .into(imageView)
    }
}

@BindingAdapter(value = ["bind:news", "bind:toReadLater"])
fun setReadLaterImage(imageView: ImageView, news: News, toReadLater: Boolean) {
    Log.d("####", "setReadLaterImage")
    if (!toReadLater(imageView.context, news)) {
        imageView.setImageResource(R.drawable.ic_bookmark_border)
    } else {
        imageView.setImageResource(R.drawable.ic_bookmark)
    }
}