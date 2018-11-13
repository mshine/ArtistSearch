package com.lloyds.artistsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jakewharton.rxrelay2.PublishRelay
import com.lloyds.artistsearch.api.ArtistResult
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(private var context: Context, private var artistList: List<ArtistResult.Artist>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return Item(view)
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Item).bindData(context, artistList[position], artistList[position].images[2])
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val clickSubject = PublishRelay.create<ArtistResult.Artist>()

        init {
            itemView.setOnClickListener {
//                clickSubject.accept()
            }
        }

        fun bindData(context: Context, artistList: ArtistResult.Artist, artworkList: ArtistResult.Artwork) {
            itemView.textView.text = artistList.name

            Glide.with(context)
                .load(artworkList.imageUrl)
                .into(itemView.imageView)
        }
    }
}