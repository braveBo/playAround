package com.redfin.hw

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.redfin.hw.image.ImageMainActivity
import com.redfin.hw.utils.IO

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FoodTruckAdapter
    private lateinit var viewModel: FoodTruckViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        luanchImageActivity()

//        setupRecyclerView()
//        setupViewModel()
//        viewModel.loadFoodTrucks()
    }

    private fun luanchImageActivity() {
        val intent = Intent(this, ImageMainActivity::class.java)
        startActivity(intent)
    }

    private fun setupRecyclerView() {
        recyclerView = findViewById(R.id.recyclerView)
        adapter = FoodTruckAdapter(this)
        recyclerView.adapter = adapter
        val dividerItemDecoration = DividerItemDecoration(recyclerView.context, DividerItemDecoration.VERTICAL)
        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun setupViewModel() {
        viewModel = FoodTruckViewModelSingletonFactory.viewModel
        viewModel.getResult().observe(this) {
            result ->
                when(result) {
                    is Result.Success -> {
                        adapter.updateList(result.foodTruckList)
                    }
                    is Result.Error -> {
                        Toast.makeText(this, result.message, Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
        }
    }
}