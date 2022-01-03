package com.example.week1wls.ui.healthcare

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R
import kotlinx.android.synthetic.main.item_food_data.view.*

class HealthAdapter(private val context: Context): RecyclerView.Adapter<HealthAdapter.ViewHolder>() {

    var data = mutableListOf<FoodData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HealthAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_food_data, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val foodName: TextView = itemView.tv_rv_foodName
        private val foodCalories: TextView = itemView.tv_rv_calories

        fun bind(item: FoodData) {
            foodName.text = item.name
            if(item.NUTR_CONT1 == null) {
                foodCalories.text = "칼로리 데이터 없음"
            } else {
                foodCalories.text = item.NUTR_CONT1.toString() + "(kcal)"
            }
        }
    }
}