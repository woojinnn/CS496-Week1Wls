package com.example.week1wls.ui.healthcare

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.week1wls.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.fragment_healthcare_login.*

class LoginFragment : Fragment() {
    private lateinit var profileCache: SharedPreferences
    private lateinit var gson: Gson

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_healthcare_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        profileCache = requireActivity().getSharedPreferences("profileCache", MODE_PRIVATE)
        gson = GsonBuilder().create()

        val savedProfile = profileCache.getString("profileCache", "")
        if(!savedProfile.equals("")) {
            return findNavController().navigate(R.id.navigation_healthcare_main)
        }

        Log.d("loginfragment", "onViewCreated1")

        btn_Done.setOnClickListener{
            // Check empty field
            if(et_name.text.toString() == "" ||
                et_height.text.toString() == "" ||
                et_weight.text.toString() == "" ||
                et_age.text.toString() == "") {
                Toast.makeText(requireContext(), "Fill every blanks", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val name = et_name.text.toString()
            val height = et_height.text.toString().toDouble()
            val weight = et_weight.text.toString().toDouble()
            val age = et_age.text.toString().toInt()
            val isMale = when (radiogroup_sex.checkedRadioButtonId) {
                R.id.radioMan -> true
                R.id.radioWoman -> false
                else -> true
            }

            val bmr = if (isMale) {
                66.47 + (13.75 * weight) + (5 * height) - (6.76 * age)
            } else {
                655.1 + (9.56 * weight) + (1.85 * height) - (4.68 * age)
            }

            val profile = Profile(name, height, weight, age, isMale, bmr, 0.toDouble())

            // Pass profile as shared Preference
            val spEditor = profileCache.edit()
            val profileStr = gson.toJson(profile, Profile::class.java)
            spEditor.putString("profileCache", profileStr)
            spEditor.commit()
            findNavController().navigate(R.id.navigation_healthcare_main)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}