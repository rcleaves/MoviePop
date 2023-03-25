package com.droidekar.mvvmbasic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.droidekar.mvvmbasic.model.Result
import com.droidekar.mvvmbasic.model.Movies
import com.droidekar.mvvmbasic.mvvmbasic.MainActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class MovieViewModel : ViewModel() {
    private var movieLiveData = MutableLiveData<ArrayList<Result>>()
    fun getPopularMovies() {

        viewModelScope.launch() {
            // hard coded results
            val gson = Gson()
            val results = ArrayList<Result>(gson.fromJson(MovieData.data, Array<Result>::class.java).asList())

            movieLiveData.value = results as ArrayList<Result>

            /*viewModelScope.launch {
                while(true) {
                    Log.d("TAG", "vm scope job running...")
                    delay(1000L)
                }
            }*/

            /*MainActivity.RetrofitInstance.api.getPopularMovies("69d66957eebff9666ea46bd464773cf0").enqueue(object :
                Callback<Movies> {
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    if (response.body()!=null){
                        //movieLiveData.value = response.body()!!.results
                        for (result in response.body()!!.results) {
                            println("result: $result")
                        }
                    }
                    else{
                        return
                    }
                }
                override fun onFailure(call: Call<Movies>, t: Throwable) {
                    Log.d("TAG",t.message.toString())
                }
            })*/
        }

    }
    fun observeMovieLiveData() : LiveData<ArrayList<Result>> {
        return movieLiveData
    }
}
