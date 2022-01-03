package com.example.week1wls.ui.healthcare

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.week1wls.R
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_healthcare_main.*

class MainFragment: Fragment() {
    private lateinit var profileCache: SharedPreferences
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
        gson = GsonBuilder().create()

        return inflater.inflate(R.layout.fragment_healthcare_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // set adapter for food list
        healthAdapter = HealthAdapter(requireContext())
        rv_foodList.adapter = healthAdapter

        setProfile()

        // Edit profile button
        btn_editProfile.setOnClickListener{
            val editor = profileCache.edit()
            editor.clear()
            editor.apply()
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
            Log.d("foods", foodList.toString())
            healthAdapter.data = foodList
            healthAdapter.notifyDataSetChanged()

            et_inputFood.setText("")
        }

        // 음식 리스트 터치됐을 때
        healthAdapter.setOnItemClickListener(object: HealthAdapter.OnItemClickListener {
            override fun onItemClick(v: View, foodData: FoodData, pos: Int) {
                // update profile
                val newProfile = Profile(profile.name, profile.height, profile.weight, profile.age, profile.isMale, profile.bmr, profile.total + foodData.NUTR_CONT1)
                val spEditor = profileCache.edit()
                val profileStr = gson.toJson(newProfile, Profile::class.java)
                spEditor.putString("profileCache", profileStr)
                spEditor.commit()
                setProfile()

                // clear recyclerView contents
                healthAdapter.data.clear()
                healthAdapter.notifyDataSetChanged()
            }
        })

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
        pieChart.setUsePercentValues(true)

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(profile.total.toFloat(),"Current"))
        val remainingCal = if(profile.bmr > profile.total) (profile.bmr - profile.total).toFloat() else 0.toFloat()
        pieEntries.add(PieEntry(remainingCal, "Total")) // Remaining

        val pieDataset = PieDataSet(pieEntries, "")
        val pieData = PieData(pieDataset)
        pieDataset.setColors(ColorTemplate.JOYFUL_COLORS, 100)
        pieDataset.setDrawValues(false)
        pieDataset.sliceSpace = 2f

        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.centerText = "${profile.total.toInt()} / ${profile.bmr.toInt()}"
        pieChart.setCenterTextColor(Color.BLACK)
        pieChart.setCenterTextSize(10f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.data = pieData
        pieChart.animate()
        pieChart.invalidate()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

//    fun findFood(input: String){
//        // linear layout
//        foodList.apply{
//            layoutManager = LinearLayoutManager(activity)
//            adapter = HealthAdapter()
//        }
//    }
}