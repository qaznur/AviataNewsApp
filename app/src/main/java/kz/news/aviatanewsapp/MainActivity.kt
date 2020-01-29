package kz.news.aviatanewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.network.Network

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job = Job()
        val mainScope = CoroutineScope(job + Dispatchers.Main)

        mainScope.launch {
            val deferred = Network.newsService.getTopHeadlines().await()
            println("MY RESPONSE: $deferred")
        }
    }
}
