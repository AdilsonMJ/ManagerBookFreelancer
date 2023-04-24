package com.example.managerbookfreelancer.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

class Extensions {

    companion object{
        fun Fragment.setActionBarTitle(title: String) {
            (requireActivity() as AppCompatActivity).supportActionBar?.title = title
        }
    }


}