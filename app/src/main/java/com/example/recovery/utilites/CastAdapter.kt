package com.example.recovery.utilites

import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recovery.R
import com.example.recovery.data.model.cast.Cast

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"

class CastAdapter(private val casts:List<Cast>) : RecyclerView.Adapter<CastAdapter.CastViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.detail_item,parent,false)
        return CastViewHolder(view)
    }

    override fun getItemCount(): Int {
        return casts.size
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        val cast=casts[position]

        if(cast.profile_path.isNullOrEmpty()){
            Glide.with(holder.castImg).load(R.drawable.noimage).into(holder.castImg)
        }else{
            Glide.with(holder.castImg).load(IMAGE_BASE_URL+cast.profile_path).into(holder.castImg)
        }
        holder.castName.text= cast.name
    }

    inner class CastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val castImg : ImageView = itemView.findViewById(R.id.detail_item_image)
        val castName : TextView =itemView.findViewById(R.id.detail_item_text)

    }
}