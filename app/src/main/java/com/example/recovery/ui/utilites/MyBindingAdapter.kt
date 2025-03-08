package com.example.recovery.ui.utilites

import android.annotation.SuppressLint
import android.text.Html
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.recovery.BuildConfig
import com.example.recovery.R
import com.example.recovery.data.model.movie.Movie

private const val IMAGE_BASE_URL = BuildConfig.IMAGE_BASE_URL


@BindingAdapter("glideLoad")
fun ImageView.glideLoad(url: String?){
    Glide.with(this).load(
        if (url.isNullOrEmpty())
            R.drawable.noimage
        else
            IMAGE_BASE_URL + url
    ).into(this)
}


@BindingAdapter("releaseTitle")
fun TextView.releaseTitle(movie: Movie){

    val htmlText = if (movie.release_date.isEmpty()) {
        "<font color='#FFFFFF'>${movie.original_title})</font>"
    } else {
        "<font color='#FFFFFF'>${movie.original_title}</font>  <font color='#7986CB'>(${
            movie.release_date.split(
                "-"
            ).get(0)
        })</font>"
    }
    this.text = Html.fromHtml(htmlText)


}

@SuppressLint("DefaultLocale")
@BindingAdapter("ratingText")
fun TextView.ratingText(rate:Double){
    val ratingNumber = String.format("%.1f", rate)
    val htmlText = "<font color='#FFFFFF'>Rating: </font>  <font color='#FFEB3B'>${ratingNumber}★</font>"
    this.text= Html.fromHtml(htmlText)

}