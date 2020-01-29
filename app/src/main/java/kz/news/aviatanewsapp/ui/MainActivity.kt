package kz.news.aviatanewsapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import kz.news.aviatanewsapp.R
import kz.news.aviatanewsapp.databinding.ActivityMainBinding
import kz.news.aviatanewsapp.ui.fragments.DetailsFragment
import kz.news.aviatanewsapp.ui.fragments.NewsListFragment

const val LAYOUT_ACTIVITY = R.layout.activity_main
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, LAYOUT_ACTIVITY)

        setSupportActionBar(binding.toolbar)
        setupViewPager()
        binding.tabLayout.setupWithViewPager(binding.viewPager)

//        val job = Job()
//        val mainScope = CoroutineScope(job + Dispatchers.Main)
//
//        mainScope.launch {
//            val deferred = Network.newsService.getTopHeadlines().await()
//            println("MY RESPONSE: $deferred")
//        }
    }

    private fun setupViewPager() {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(NewsListFragment(), "Top")
        adapter.addFragment(DetailsFragment(), "All")
        binding.viewPager.adapter = adapter
    }
}
