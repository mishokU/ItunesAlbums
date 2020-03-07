package com.example.itunesmusic.domain.converters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.example.itunesmusic.R
import com.squareup.picasso.Picasso

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String){
    Picasso.get().load(url).placeholder(R.drawable.album_no_image).into(imageView)
}

