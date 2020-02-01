package kz.news.aviatanewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.adapters.PagingNewsListAdapter
import kz.news.aviatanewsapp.databinding.ActivityReadLaterBinding
import kz.news.aviatanewsapp.viewmodels.ReadLaterViewModel

class ReadLaterActivity : AppCompatActivity() {

    private lateinit var viewModel: ReadLaterViewModel
    private lateinit var binding: ActivityReadLaterBinding
    private lateinit var listAdapter: PagingNewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_read_later)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.read_later)
        }

        viewModel = ViewModelProviders.of(this, ReadLaterViewModel.Factory(application))
            .get(ReadLaterViewModel::class.java)
        viewModel.readLaterNews.observe(this, Observer { news ->
            listAdapter.submitList(news)
        })

        listAdapter = PagingNewsListAdapter(PagingNewsListAdapter.ClickListenerImpl(this, viewModel))
        val layoutManager = LinearLayoutManager(this)
        binding.newsList.adapter = listAdapter
        binding.newsList.layoutManager = layoutManager
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
