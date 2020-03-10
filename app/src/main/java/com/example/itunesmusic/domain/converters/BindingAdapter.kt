package com.example.itunesmusic.domain.converters

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.itunesmusic.R
import com.example.itunesmusic.domain.models.SingleTrackModel
import com.example.itunesmusic.ui.adapters.AlbumPlayListAdapter
import com.example.itunesmusic.ui.adapters.AllAlbumsRecyclerViewAdapter
import com.squareup.picasso.Picasso

enum class NetworkStatus { LOADING, DONE, ERROR}

const val DELETE_ALBUMS = "Delete all albums from db"
const val SORT = "Albums sorted by album name"


@BindingAdapter("bindList")
fun bindList(recyclerView: RecyclerView, data : List<SingleTrackModel>?){
    val adapter = recyclerView.adapter as AlbumPlayListAdapter
    adapter.bindList(data)
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String){
    Picasso.get().load(url).placeholder(R.drawable.album_no_image).into(imageView)
}

@BindingAdapter("year")
fun bindDate(textView: TextView, date : String?){
    val list = date?.split("-")
    textView.text = list?.get(0)
}

@BindingAdapter("trackTime")
fun bindTrackTime(textView: TextView, milliseconds  :Int){
    val minutes = milliseconds / 1000 / 60
    val seconds = milliseconds / 1000 % 60
    val secString : String
    if(seconds < 10){
        secString = "0$seconds"
        textView.text = ("$minutes:$secString").toString()
    } else {
        textView.text = ("$minutes:$seconds").toString()
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("price")
fun bindPrice(textView: TextView, price : Double){
    textView.text = (price).toString() + "$"
}

fun bindProgressBar(progressBar: ProgressBar, networkStatus: NetworkStatus?){
    when(networkStatus){
        NetworkStatus.LOADING -> progressBar.visibility = View.VISIBLE
        NetworkStatus.ERROR -> progressBar.visibility = View.INVISIBLE
        NetworkStatus.DONE -> progressBar.visibility = View.INVISIBLE
    }
}

fun bindEmptyList(textView: TextView, networkStatus: NetworkStatus?){
    when(networkStatus){
        NetworkStatus.LOADING -> textView.visibility = View.INVISIBLE
        NetworkStatus.DONE -> textView.visibility = View.INVISIBLE
        NetworkStatus.ERROR -> textView.visibility = View.VISIBLE
    }
}