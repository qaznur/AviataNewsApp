package kz.news.aviatanewsapp.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.adapters.PagingNewsListAdapter
import kz.news.aviatanewsapp.databinding.FragmentEverythingBinding
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.ui.DetailsActivity
import kz.news.aviatanewsapp.utils.onScrolledToEnd
import kz.news.aviatanewsapp.viewmodels.EverythingViewModel

class EverythingFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var viewModel: EverythingViewModel
    private lateinit var binding: FragmentEverythingBinding
    private lateinit var listAdapter: PagingNewsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_everything, container, false)

        viewModel = ViewModelProviders.of(this, EverythingViewModel.Factory(activity!!.application))
            .get(EverythingViewModel::class.java)

        binding.refreshLayout.setOnRefreshListener(this)
        viewModel.everything.observe(this, Observer { news ->
            news?.let {
                listAdapter.submitList(news)
            }
            binding.refreshLayout.isRefreshing = false
        })

        listAdapter = PagingNewsListAdapter(PagingNewsListAdapter.ClickListenerImpl(context, viewModel))
        val layoutManager = LinearLayoutManager(context)
        binding.newsList.adapter = listAdapter
        binding.newsList.layoutManager = layoutManager
        binding.newsList.onScrolledToEnd(layoutManager) {
            viewModel.loadNextIfAvailable()
        }
        return binding.root
    }

    override fun onRefresh() {
        viewModel.onSwipeRefreshed()
    }

    override fun onStart() {
        super.onStart()
        Log.d("#####", "all onStart")
        viewModel.checkForChanges()
    }

    override fun onStop() {
        super.onStop()
        Log.d("#####", "all onStop")
    }
}
