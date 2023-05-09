package com.redfin.hw.image

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.redfin.hw.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class StaticImageActivity : AppCompatActivity() {
    private val imageUrl = "https://picsum.photos/200/300"
    private lateinit var button: Button
    private lateinit var imageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_static_image)

        button = findViewById<Button>(R.id.button)
        imageView = findViewById<ImageView>(R.id.myImageView)

        button.setOnClickListener {
            downloadImage(imageUrl) {
                imageView.setImageBitmap(it)
                button.visibility = View.GONE
                imageView.visibility = View.VISIBLE
            }
        }
    }

    private fun downloadImage(url: String, callback: (Bitmap) -> Unit) {
        GlobalScope.launch {
            val bitmap = try {
                val inputStream = URL(imageUrl).openStream()
                BitmapFactory.decodeStream(inputStream)
            } catch (e : Exception) {
                null
            }
            withContext(Dispatchers.Main) {
                if (bitmap != null) {
                    callback(bitmap)
                } else {
                    Toast.makeText(this@StaticImageActivity,
                        "fails to download", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}