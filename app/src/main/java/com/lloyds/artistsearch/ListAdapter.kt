package com.lloyds.artistsearch

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lloyds.artistsearch.api.ArtistResult
import com.lloyds.artistsearch.api.ImageSize
import com.lloyds.artistsearch.injection.module.GlideApp
import kotlinx.android.synthetic.main.list_item.view.*

class ListAdapter(private var context: Context, var artistList: List<ArtistResult.Artist>, private val listener: Listener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    interface Listener {
        fun onItemClick(artist: ArtistResult.Artist)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return Item(view).apply {
            view.setOnClickListener { listener.onItemClick(artist) }
        }
    }

    override fun getItemCount(): Int {
        return artistList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as Item).bindData(context, artistList[position], artistList[position].images.first { it.imageSize == ImageSize.Large.size })
    }

    class Item(itemView: View) : RecyclerView.ViewHolder(itemView) {

        lateinit var artist: ArtistResult.Artist

        fun bindData(context: Context, artist: ArtistResult.Artist, artwork: ArtistResult.Artwork) {
            this.artist = artist
            GlideApp.with(context)
                .load(artwork.imageUrl)
                .placeholder(ColorDrawable(Color.GRAY))
                .error(R.drawable.icn_sad)
                .into(itemView.list_item_imageview)

            itemView.list_item_textview.text = artist.name
        }
    }
}