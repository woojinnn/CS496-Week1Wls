package com.example.week1wls.ui.healthcare

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
import kotlinx.android.synthetic.main.fragment_healthcare_login.*

class LoginFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_healthcare_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            val height = et_height.text.toString().toInt()
            val weight = et_weight.text.toString().toInt()
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

            val profile = Profile(name, height, weight, age, isMale, bmr)

            // Pass profile as bundle
            val healthcareMainfrag = MainFragment()
            val bundle = Bundle()
            bundle.putParcelable("profile", profile)
            healthcareMainfrag.arguments = bundle

            val action = LoginFragmentDirections.actionNavigationNotificationsToNavigationHealthcareMain(profile)
            findNavController().navigate(action)
            //            findNavController().navigate(R.id.navigation_healthcare_main, bundle)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
    }
}