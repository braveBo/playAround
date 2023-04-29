package com.redfin.hw.image

import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redfin.hw.R
import com.redfin.hw.data.ImageItem
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ImageMainActivityDynamicLayout : AppCompatActivity() {
    private lateinit var layout: LinearLayout
    private lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_dynamic_layout)

        layout = findViewById(R.id.dynamic_layout)

        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        if (savedInstanceState == null) {
            viewModel.load()
            lifecycleScope.launch {
                viewModel.images.collect {
                    buildTextView(it)
                }
            }
        }
    }

    private fun buildString(list: List<ImageItem>): String {
        var newList = list.sortedBy { it.author }

        val buffer = StringBuffer();
        for (item in newList) {
            buffer.append(item.author)
            buffer.append("\n")
        }
        return buffer.toString()
    }

    private fun buildTextView(list: List<ImageItem>) {
        val textView = TextView(this)
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(16, 16, 16, 16)

        textView.text = buildString(list)
        layout.addView(textView)
    }
}