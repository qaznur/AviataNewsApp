package kz.news.aviatanewsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kz.news.aviatanewsapp.network.Network
import kz.news.aviatanewsapp.network.NewsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val job = Job()
        val mainScope = CoroutineScope(job + Dispatchers.Main)

        mainScope.launch {
            val deferred = Network.newsService.getEverything().await()
            println("MY RESPONSE: $deferred")
        }
    }
}
