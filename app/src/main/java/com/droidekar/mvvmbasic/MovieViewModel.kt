package com.droidekar.mvvmbasic

import android.util.Log
import androidx.lifecycle.*

import com.droidekar.mvvmbasic.model.Result
import com.droidekar.mvvmbasic.model.Movies
import com.droidekar.mvvmbasic.mvvmbasic.MainActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

import retrofit2.Response
import retrofit2.Call
import retrofit2.Callback

class MovieViewModel : ViewModel() {

    private var movieLiveData = MutableLiveData<ArrayList<Result>>()



    fun getPopularMovies() {

        viewModelScope.launch() {
            /*// hard coded results
            val gson = Gson()
            val results = ArrayList<Result>(gson.fromJson(MovieData.data, Array<Result>::class.java).asList())

            movieLiveData.value = results as ArrayList<Result>*/

            /*viewModelScope.launch {
                while(true) {
                    Log.d("TAG", "vm scope job running...")
                    delay(1000L)
                }
            }*/

                val response = MainActivity.RetrofitInstance.api.getPopularMovies("69d66957eebff9666ea46bd464773cf0")
                if (response.isSuccessful) {
                    response.body()?.let {
                        val list = arrayListOf<Result>()
                        /*for (result in it.results) {
                            Log.d("TAG", "result: $result")
                            list.add(result)
                        }*/
                        movieLiveData.value = it.results.toCollection(list)
                        //movieLiveData.value = list
                    }
                } else {
                    Log.w("TAG", "could not get movies")
                }
                /*MainActivity.RetrofitInstance.api.getPopularMovies("69d66957eebff9666ea46bd464773cf0").enqueue(object :
                Callback<Movies> {
                override fun onResponse(call: Call<Movies>, response: Response<Movies>) {
                    response.body()?.let {
                        movieLiveData.value = response.body().results
                        for (result in response.body()!!.results) {
                            println("result: $result")
                        }
                    }
                    if (response.body()!=null){
                        movieLiveData.value = response.body()!!.results
                        for (result in response.body()!!.results) {
                            println("result: $result")
                        }
                    }
                    else{
                        return
                    }
                }
                override fun onFailure(call: Call<Movies>, t: Throwable) {
                    Log.e("TAG",t.message.toString(), t)
                }
            })*/
        }
    }

    fun observeMovieLiveData() : LiveData<ArrayList<Result>> {
        return movieLiveData
    }

    /*override fun onCreate(owner: LifecycleOwner) {
        super.onCreate(owner)
        Log.d("TAG", "<onCreate>")
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        Log.d("TAG", "<onStop>")
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        Log.d("TAG", "<onDestroy>")
    }

    override fun onStart(owner: LifecycleOwner) {
        super.onStart(owner)
        Log.d("TAG", "<onStart>")
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        Log.d("TAG", "<onResume>")
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        Log.d("TAG", "<onPause>")
        }
    */
}
