package kz.news.aviatanewsapp.ui.fragments


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
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.adapters.PagingNewsListAdapter
import kz.news.aviatanewsapp.databinding.FragmentTopHeadlinesBinding
import kz.news.aviatanewsapp.utils.onScrolledToEnd
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

        viewModel.topHeadlines.observe(this, Observer { news ->
            news?.let {
                listAdapter.submitList(news)
            }
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

    override fun onStart() {
        super.onStart()
        Log.d("#####", "top onStart")
        viewModel.checkForChanges()
    }

    override fun onStop() {
        super.onStop()
        Log.d("#####", "top onStop")
    }
}
