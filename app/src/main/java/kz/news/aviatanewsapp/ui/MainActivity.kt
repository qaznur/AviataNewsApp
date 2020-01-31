package kz.news.aviatanewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.adapters.ViewPagerAdapter
import kz.news.aviatanewsapp.databinding.ActivityMainBinding
import kz.news.aviatanewsapp.ui.fragments.EverythingFragment
import kz.news.aviatanewsapp.ui.fragments.TopHeadlinesFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setSupportActionBar(binding.toolbar)
        setupViewPager()
        binding.tabLayout.setupWithViewPager(binding.viewPager)
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopHeadlinesFragment(), "Top")
        adapter.addFragment(EverythingFragment(), "All")
        binding.viewPager.adapter = adapter
    }
}
