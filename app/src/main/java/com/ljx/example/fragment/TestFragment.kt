package com.ljx.example.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment

class TestFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TestFragmentDirections.gotoHome()
    }
}