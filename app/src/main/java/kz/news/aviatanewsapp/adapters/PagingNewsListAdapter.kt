package kz.news.aviatanewsapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.databinding.ListitemNewsBinding
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.ui.fragments.TopHeadlinesFragment

class PagingNewsListAdapter(private val clickListener: ClickListener) :
    PagedListAdapter<News, PagingNewsListAdapter.NewsViewHolder>(NewsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
//        val newsItem = getItem(position)
//        holder.bind(newsItem, clickListener)

//        getItem(position)?.let {
//            holder.bind(it, clickListener)
//        }
    }


    class NewsViewHolder(private val binding: ListitemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(news: News, clickListener: TopHeadlinesFragment.ClickListenerImpl) {
            binding.news = news
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): NewsViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding: ListitemNewsBinding =
                    DataBindingUtil.inflate(layoutInflater, R.layout.listitem_news, parent, false)

                return NewsViewHolder(binding)
            }
        }
    }


    class NewsDiffCallback : DiffUtil.ItemCallback<News>() {

        override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
            return oldItem == newItem
        }
    }

    interface ClickListener{
        fun onMarked(news: News)
        fun onClicked(news: News)
    }
}

