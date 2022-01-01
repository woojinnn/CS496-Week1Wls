package com.example.week1wls.ui.healthcare

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.week1wls.R
import com.example.week1wls.ui.dashboard.DashboardViewModel
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class MainFragment: Fragment() {
//    private val args:MainFragmentArgs by navArgs()

    private lateinit var profileCache: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileCache = requireActivity().getSharedPreferences("profileCache",
            Context.MODE_PRIVATE
        )
        gson = GsonBuilder().create()

        val view = inflater.inflate(R.layout.fragment_healthcare_main, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val profileStr = profileCache.getString("profileCache", "")
        val profile = gson.fromJson(profileStr, Profile::class.java)

        Log.d("profile", profile.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}