package com.droidekar.mvvmbasic.mvvmbasic

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.droidekar.mvvmbasic.MovieAdapter
import com.droidekar.mvvmbasic.MovieApi
import com.droidekar.mvvmbasic.MovieViewModel
import com.droidekar.mvvmbasic.databinding.ActivityMainBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MovieViewModel
    private lateinit var movieAdapter : MovieAdapter

    private lateinit var lifecycleJob: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareRecyclerView()
        viewModel = ViewModelProvider(this)[MovieViewModel::class.java]
        viewModel.getPopularMovies()
        viewModel.observeMovieLiveData().observe(this, Observer { movieList ->
            movieAdapter.setMovieList(movieList)
        })
        /*lifecycleJob = lifecycleScope.launch {
            while(true) {
                Log.d("TAG", "lifecycle scope job running...")
                delay(1000L)
            }
        }*/
        binding.button.setOnClickListener {
            finish()
        }

    }

    private fun prepareRecyclerView() {
        movieAdapter = MovieAdapter()
        binding.rvMovies.apply {
            layoutManager = GridLayoutManager(applicationContext,2)
            adapter = movieAdapter
        }
    }

    object RetrofitInstance {
        val api : MovieApi by lazy {

            val client = OkHttpClient.Builder()
                .build()

            Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/movie/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(MovieApi::class.java)
        }
    }
    override fun onPause() {
        super.onPause()
        Log.d("TAG", "onPause called")
    }
    override fun onStop() {
        super.onStop()
        Log.d("TAG", "onStop called")
        //lifecycleJob.cancel()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("TAG", "onDestroy called")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}
