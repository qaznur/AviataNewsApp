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
import kz.news.aviatanewsapp.adapters.PagingNewsListAdapter
import kz.news.aviatanewsapp.ui.DetailsActivity
import kz.news.aviatanewsapp.viewmodels.TopHeadlinesViewModel

class TopHeadlinesFragment : Fragment() {

    private lateinit var viewModel: TopHeadlinesViewModel
    private lateinit var binding: FragmentTopHeadlinesBinding
    private lateinit var listAdapter: PagingNewsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_top_headlines, container, false)
        binding.lifecycleOwner = this

        viewModel = ViewModelProviders.of(this, TopHeadlinesViewModel.Factory(activity!!.application))
            .get(TopHeadlinesViewModel::class.java)

        viewModel.pagedList.observe(this, Observer { news ->
            Log.d("#####", "tops $news")
            listAdapter.submitList(news)
        })

        listAdapter = PagingNewsListAdapter(ClickListenerImpl())
        binding.newsList.adapter = listAdapter
        return binding.root
    }

    inner class ClickListenerImpl: PagingNewsListAdapter.ClickListener {

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
