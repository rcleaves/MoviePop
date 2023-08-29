package com.droidekar.mvvmbasic

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.droidekar.mvvmbasic.databinding.MoviesLayoutBinding
import com.droidekar.mvvmbasic.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private var movieList = ArrayList<Result>()
    fun setMovieList(movieList: ArrayList<Result>) {
        //this.movieList = movieList as ArrayList<Result>
        this.movieList = movieList
        notifyDataSetChanged()
    }

    class ViewHolder(val binding: MoviesLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MoviesLayoutBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val url = "https://image.tmdb.org/t/p/w500" + movieList[position].poster_path
        Glide.with(holder.itemView)
            .load(url)
            .into(holder.binding.movieImage)
        holder.binding.movieName.text = movieList[position].title

        // get year of release
        val date = LocalDate.parse(movieList[position].release_date, DateTimeFormatter.ISO_DATE)
        //holder.binding.releaseDate.text = "(" + date.year + ")"
        holder.binding.releaseDate.text = "(" + movieList[position].vote_average.toString() + ")"

        GlobalScope.launch(Dispatchers.Main) {
            holder.binding.movieImage.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                // show all movie details
                val url = "https://themoviedb.org/movie/" + movieList[position].id
                intent.setData(Uri.parse(url))
                startActivity(holder.binding.movieImage.context, intent, null)
            }
        }

    }

    override fun getItemCount(): Int {
        return movieList.size
    }
}
