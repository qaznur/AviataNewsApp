package kz.news.aviatanewsapp.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import kz.news.aviatanewsapp.R


@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String) {
    Log.d("#####", "imageUrl")
    Glide.with(imageView.context)
        .load(url)
        .into(imageView)
}

@BindingAdapter("srcLaterRead")
fun setReadLaterImage(imageView: ImageView, toReadLater: Boolean) {
    Log.d("#####", "setReadLaterImage")
    if (toReadLater) {
        imageView.setImageResource(R.drawable.ic_bookmark)
        Log.d("#####", "toReadLater")
    } else {
        imageView.setImageResource(R.drawable.ic_bookmark_border)
        Log.d("#####", "NOT toReadLater")
    }
}