package com.redfin.hw

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FoodTruckAdapter
    private lateinit var viewModel: FoodTruckViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupRecyclerView()
        setupViewModel()
        viewModel.loadFoodTrucks()
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