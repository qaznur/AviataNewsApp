package kz.news.aviatanewsapp.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.databinding.FragmentTopHeadlinesBinding
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.adapters.NewsListAdapter
import kz.news.aviatanewsapp.ui.DetailsActivity
import kz.news.aviatanewsapp.viewmodels.MainActivityViewModel

class TopHeadlinesFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: FragmentTopHeadlinesBinding
    private lateinit var listAdapter: NewsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_headlines, container, false)

        listAdapter = NewsListAdapter(ClickListenerImpl())
        binding.newsList.adapter = listAdapter
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d("#####", "onActivityCreated")

        viewModel = ViewModelProviders.of(activity!!, MainActivityViewModel.Factory(activity!!.application))
            .get(MainActivityViewModel::class.java)
        viewModel.topNews.observe(this, Observer { news ->
            Log.d("#####", "news $news")
            listAdapter.submitList(news)
        })
    }

    interface ClickListener{
        fun onMarked(news: News)
        fun onClicked(news: News)
    }

    inner class ClickListenerImpl: ClickListener {

        override fun onMarked(news: News) {
            viewModel.saveForLateRead(news)
        }

        override fun onClicked(news: News) {
            Log.d("#####", "onClicked")
            val intent = Intent(context, DetailsActivity::class.java)
            intent.putExtra("news", news)
            context?.startActivity(intent)
        }

    }
}
