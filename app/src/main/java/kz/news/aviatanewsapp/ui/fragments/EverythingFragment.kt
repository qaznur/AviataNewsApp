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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.adapters.PagingNewsListAdapter
import kz.news.aviatanewsapp.databinding.FragmentEverythingBinding
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.ui.DetailsActivity
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
        viewModel.pagedList.observe(this, Observer { news ->
            Log.d("######", "everything $news")
            listAdapter.submitList(news)
            binding.refreshLayout.isRefreshing = false
        })

        listAdapter = PagingNewsListAdapter(ClickListenerImpl())
        binding.newsList.adapter = listAdapter
        return binding.root
    }

    override fun onRefresh() {
        Log.d("######", "onRefresh")
        viewModel.onSwipeRefreshed()
    }


    inner class ClickListenerImpl : PagingNewsListAdapter.ClickListener {

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
