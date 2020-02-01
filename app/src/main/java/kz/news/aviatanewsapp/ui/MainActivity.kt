package kz.news.aviatanewsapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
        adapter.addFragment(TopHeadlinesFragment(), getString(R.string.top))
        adapter.addFragment(EverythingFragment(), getString(R.string.all))
        binding.viewPager.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.read_later) {
            val intent = Intent(this, ReadLaterActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
