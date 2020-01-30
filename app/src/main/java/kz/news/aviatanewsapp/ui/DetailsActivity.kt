package kz.news.aviatanewsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.databinding.ActivityDetailsBinding
import kz.news.aviatanewsapp.domain.News
import kz.news.aviatanewsapp.viewmodels.DetailsViewModel

class DetailsActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: ActivityDetailsBinding
    private lateinit var factory: DetailsViewModel.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_details)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
        }

        val news = intent.getSerializableExtra("news")
        factory = DetailsViewModel.Factory(application, news as News)
        viewModel = ViewModelProviders.of(this, factory).get(DetailsViewModel::class.java)
        binding.viewModel = viewModel
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

}
