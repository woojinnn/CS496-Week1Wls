package com.example.week1wls.ui.healthcare

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.week1wls.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.fragment_healthcare_main.*

class MainFragment: Fragment() {
    private lateinit var profileCache: SharedPreferences
    private lateinit var foodHistoryCache: SharedPreferences
    private lateinit var foodHistory: MutableList<FoodData>
    private lateinit var gson: Gson
    private lateinit var profile: Profile
    private lateinit var healthAdapter: HealthAdapter
    private lateinit var input : String
    private lateinit var foodList: MutableList<FoodData>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        profileCache = requireActivity().getSharedPreferences(
            "profileCache",
            Context.MODE_PRIVATE
        )

        foodHistoryCache = requireActivity().getSharedPreferences(
            "foodHistoryCache",
            Context.MODE_PRIVATE
        )

        gson = GsonBuilder().create()

        return inflater.inflate(R.layout.fragment_healthcare_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set adapter for food list
        healthAdapter = HealthAdapter(requireContext())
        rv_foodList.adapter = healthAdapter

        updateFoodHistory()
        setProfile()

        // Edit profile button
        btn_editProfile.setOnClickListener{
            val editor = profileCache.edit()
            editor.clear()
            editor.apply()

            val foodEditor = foodHistoryCache.edit()
            foodEditor.clear()
            foodEditor.apply()
            findNavController().navigate(R.id.action_navigation_healthcare_main_to_navigation_notifications)
        }

        /* input 받기 */
        // search 버튼 눌렸을 때
        btn_searchFood.setOnClickListener {
            input = et_inputFood.text.toString()

            val thread = ApiFoodInfo(input)
            thread.start()
            thread.join()
            foodList = thread.foodList
            healthAdapter.data = foodList
            healthAdapter.notifyDataSetChanged()

            et_inputFood.setText("")
        }

        // 음식 리스트 터치됐을 때
        healthAdapter.setOnItemClickListener(object: HealthAdapter.OnItemClickListener {
            override fun onItemClick(v: View, foodData: FoodData, pos: Int) {
                val weight = if(et_inputWeight.text.toString() != "") {
                    et_inputWeight.text.toString().toFloat()
                } else {
                    foodData.SERVING_WT.toFloat()
                }
                et_inputWeight.setText("")

                // update foodHistory
                foodHistory.forEach {
                    if(it.name == foodData.name) {
                        val prevCnt = it.weight
                        it.weight = prevCnt + weight
                    }
                }
                if(foodHistory.find {it.name == foodData.name } == null) {
                    // if the element was not in foodData
                    foodHistory.add(foodData.copy(weight = weight))
                }

                // update sharedPreference
                val spEditor = foodHistoryCache.edit()
                val type = object: TypeToken<MutableList<FoodData>>() {}
                val foodHistoryStr = gson.toJson(foodHistory, type.type)
                spEditor.putString("foodHistoryCache", foodHistoryStr)
                spEditor.commit()

                // update foodHistory
                updateFoodHistory()

                setPieChart()

                // clear recyclerView contents
                healthAdapter.data.clear()
                healthAdapter.notifyDataSetChanged()
            }
        })

        // show daily report
        btn_dailyReport.setOnClickListener {
            val foodNames = mutableListOf<String>()
            for (food in foodHistory) {
                foodNames.add("${food.name} ${food.weight}g (${food.NUTR_CONT1 / food.SERVING_WT * food.weight})kcal")
            }
            AlertDialog.Builder(requireContext())
                .setItems(
                    foodNames.toTypedArray(), object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface, which: Int) {

                            // update foodHistory
                            foodHistory.removeAt(which)

                            // update sharedPreference
                            val spEditor = foodHistoryCache.edit()
                            val type = object: TypeToken<MutableList<FoodData>>() {}
                            val foodHistoryStr = gson.toJson(foodHistory, type.type)
                            spEditor.putString("foodHistoryCache", foodHistoryStr)
                            spEditor.commit()

                            // update foodHistory
                            updateFoodHistory()

                            setPieChart()

                            dialog.dismiss()
                        }
                    }
                ).create().show()
        }
    }

    private fun setProfile() {
        getProfileInfo()

        setProfileTexts()
        setPieChart()
    }

    // get profile information
    private fun getProfileInfo() {
        val profileStr = profileCache.getString("profileCache", "")
        profile = gson.fromJson(profileStr, Profile::class.java)
    }

    private fun updateFoodHistory() {
        val foodHistoryStr = foodHistoryCache.getString("foodHistoryCache", "")
        if(foodHistoryStr == "") {
            foodHistory = mutableListOf<FoodData>()
        } else {
            val type = object: TypeToken<MutableList<FoodData>>() {}
            foodHistory = gson.fromJson(foodHistoryStr, type.type) as MutableList<FoodData>
        }
    }

    private fun setProfileTexts() {
        tv_name.text = profile.name
        if(profile.isMale)
            tv_agesex.text = "(남) ${profile.age}세"
        else
            tv_agesex.text = "(여) ${profile.age}세"
        tv_physical.text = "${profile.height} (cm), ${profile.weight} (kg)"
    }

    private fun setPieChart() {
        // Pie chart
        pieChart.setTouchEnabled(true)
        pieChart.setUsePercentValues(true)

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(totalCalories(),"Current"))
        val remainingCal = if(profile.bmr > totalCalories()) (profile.bmr - totalCalories()).toFloat() else 0.toFloat()
        pieEntries.add(PieEntry(remainingCal, "Total")) // Remaining

        val pieDataset = PieDataSet(pieEntries, "")
        val pieData = PieData(pieDataset)
        pieDataset.setColors(ColorTemplate.JOYFUL_COLORS, 100)
        pieDataset.setDrawValues(false)
        pieDataset.sliceSpace = 2f

        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.centerText = "${totalCalories().toInt()} / ${profile.bmr.toInt()}"
        pieChart.setCenterTextColor(Color.BLACK)
        pieChart.setCenterTextSize(10f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.data = pieData
        pieChart.animate()
        pieChart.invalidate()
    }

    private fun totalCalories(): Float {
        var totalCal = 0.toFloat()

        for (food in foodHistory) {
            totalCal += food.weight * (food.NUTR_CONT1 / food.SERVING_WT).toFloat()
        }

        return totalCal
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}