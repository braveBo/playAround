package com.redfin.hw

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.redfin.hw.data.FoodTruck
import com.redfin.hw.databinding.FoodTruckItemBinding

class FoodTruckAdapter(private val context: Context, private var foodTrucks: List<FoodTruck> = emptyList()):
    RecyclerView.Adapter<FoodTruckAdapter.FoodTruckViewHolder>() {

    inner class FoodTruckViewHolder(private var binding: FoodTruckItemBinding): RecyclerView.ViewHolder(binding.root)
    , View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        fun bind(foodTruck: FoodTruck) {
            val openTime = "${foodTruck.starttime} - ${foodTruck.endtime}"
            binding.foodTruckName.text = foodTruck.applicant
            binding.foodTruckAddress.text = foodTruck.location
            binding.foodTruckDescription.text = foodTruck.optionaltext
            binding.foodTruckOpeningHours.text = openTime
        }

        override fun onClick(view: View?) {
            view?.let {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedFoodTruck = foodTrucks[position]
                    val intent = Intent(context, MapActivity::class.java)
                    intent.putExtra(ARG_FOOD_TRUCK, clickedFoodTruck)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodTruckViewHolder {
        return FoodTruckViewHolder(FoodTruckItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: FoodTruckViewHolder, position: Int) {
        val foodTruck = foodTrucks[position]
        holder.bind(foodTruck)
    }

    override fun getItemCount(): Int {
        return foodTrucks.size
    }

    fun updateList(foodTrucks: List<FoodTruck> = emptyList()) {
        val diffResult = DiffUtil.calculateDiff(FoodTruckDiffCallback(this.foodTrucks, foodTrucks))
        this.foodTrucks = foodTrucks
        diffResult.dispatchUpdatesTo(this)
    }

    class FoodTruckDiffCallback(private val oldList: List<FoodTruck>, private val newList: List<FoodTruck>) :
        DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].locationid == newList[newItemPosition].locationid
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }


    companion object {
        const val ARG_FOOD_TRUCK = "FOOD_TRUCK"
    }
}