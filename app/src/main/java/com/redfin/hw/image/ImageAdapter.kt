package com.redfin.hw.image

import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.notifyAll
import org.w3c.dom.Text
import java.net.URL

class ImageAdapter: RecyclerView.Adapter<ViewHolder>() {
    private var images: List<ImageItem> = emptyList()
    private val listeners: MutableList<OnItemClickListener> = ArrayList()

    inner class ImageViewHolder(itemView: View) : ViewHolder(itemView), View.OnClickListener {
       private val imageView: ImageView = itemView.findViewById(R.id.imageView)
       private val textView: TextView = itemView.findViewById(R.id.textView)
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(image: ImageItem) {
           textView.text = image.author
           Glide.with(imageView.context).load(image.download_url).into(imageView)
            BitmapFactory.decodeStream(itemView.context.contentResolver.openInputStream(Uri.parse(image.download_url)))
       }

        override fun onClick(view: View?) {
            for (listener in listeners) {
                listener.onItemClick(images[adapterPosition])
            }
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

    fun OnItemClickListener(listener: OnItemClickListener) {
        listeners.add(listener)
    }
}

interface OnItemClickListener {
    fun onItemClick(item: ImageItem)
}