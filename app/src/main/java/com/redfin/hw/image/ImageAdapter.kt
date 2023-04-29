package com.redfin.hw.image

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.redfin.hw.R
import com.redfin.hw.data.ImageItem
import okhttp3.internal.notifyAll
import org.w3c.dom.Text

class ImageAdapter: RecyclerView.Adapter<ViewHolder>() {
    private var images: List<ImageItem> = emptyList()

    inner class ImageViewHolder(itemView: View) : ViewHolder(itemView) {
       private val imageView: ImageView = itemView.findViewById(R.id.imageView)
       private val textView: TextView = itemView.findViewById(R.id.textView)
       fun bind(image: ImageItem) {
           textView.text = image.author
           Glide.with(imageView.context).load(image.download_url).into(imageView)
       }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.image_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       (holder as ImageViewHolder).bind(images[position])
    }

    fun load(images: List<ImageItem>) {
        this.images = images
        notifyDataSetChanged()
    }
}