package kz.news.aviatanewsapp.ui.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.databinding.ActivityMainBinding
import kz.news.aviatanewsapp.viewmodels.MainActivityViewModel

const val LAYOUT = R.layout.fragment_top_headlines
class NewsListFragment : Fragment() {

    private lateinit var viewModel: MainActivityViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(LAYOUT, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(MainActivityViewModel::class.java)
        viewModel.topNews.observe(this, Observer { news ->
            print("##### $news")
        })
    }


}
