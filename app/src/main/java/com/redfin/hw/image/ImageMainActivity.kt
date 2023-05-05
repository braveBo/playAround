package com.redfin.hw.image

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redfin.hw.MainActivity
import com.redfin.hw.R
import com.redfin.hw.data.ImageItem
import kotlinx.coroutines.launch

class ImageMainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewModel: ImageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[ImageViewModel::class.java]

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = ImageAdapter()
        recyclerView.adapter = adapter

        adapter.OnItemClickListener(this)

        if (savedInstanceState == null) {
            viewModel.load()

            lifecycleScope.launch {
                viewModel.images.collect { imageItems ->
                    adapter.load(imageItems)
                }
            }

            lifecycleScope.launch {
                viewModel.error.collect { error ->
                    println(error?.message)
                }
            }
        }
    }

    override fun onItemClick(item: ImageItem) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}