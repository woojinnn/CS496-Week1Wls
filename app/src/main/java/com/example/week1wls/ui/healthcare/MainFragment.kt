package com.example.week1wls.ui.healthcare

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.week1wls.R
import com.example.week1wls.ui.dashboard.DashboardViewModel

class MainFragment: Fragment() {
    private val args:MainFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_healthcare_main, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Log.d("profile", args.profile.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}