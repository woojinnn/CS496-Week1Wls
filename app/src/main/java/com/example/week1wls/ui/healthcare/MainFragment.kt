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

//import com.github.mikephil.charting.charts.PieChart

class MainFragment: Fragment() {
//    private val args:MainFragmentArgs by navArgs()

    private lateinit var profileCache: SharedPreferences
    private lateinit var gson: Gson
    private var layoutManager: RecyclerView.LayoutManager? = null
//    private var adapter: RecyclerView.Adapter<HealthAdapter.ViewHolder>? = null
    lateinit var input : String


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

        val profileStr = profileCache.getString("profileCache", "")
        val profile = gson.fromJson(profileStr, Profile::class.java)

        Log.d("profile", profile.toString())

        // information setting
        tv_name.text = profile.name
        if(profile.isMale)
            tv_agesex.text = "(남) " + profile.age + "세"
        else
            tv_agesex.text = "(여) " + profile.age + "세"
        tv_physical.text = profile.height.toString() + " (cm), " + profile.weight.toString() + " (kg)"

        // Pie chart
        pieChart.setUsePercentValues(true)

        val pieEntries = ArrayList<PieEntry>()
        pieEntries.add(PieEntry(profile.total.toFloat(),"Current"))
        pieEntries.add(PieEntry((profile.bmr - profile.total).toFloat(), "Total")) // Remaining

        val pieDataset = PieDataSet(pieEntries, "")
        val pieData = PieData(pieDataset)
        pieDataset.setColors(ColorTemplate.JOYFUL_COLORS, 100)
        pieDataset.setDrawValues(false)
        pieDataset.sliceSpace = 2f

        pieChart.legend.isEnabled = false
        pieChart.description.isEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.centerText = "${profile.total} / ${profile.bmr}"
        pieChart.setCenterTextColor(Color.BLACK)
        pieChart.setCenterTextSize(10f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)
        pieChart.data = pieData
        pieChart.animate()
        pieChart.invalidate()

        // Edit profile button


//        /* input 받기 */
//        // search 버튼 눌렸을 때
//        fdsrhBtn.setOnClickListener {
//            input = inputFood.text.toString()
//            inputText.text = null
//            findFood(input)
//        }

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