package di

import android.os.Bundle
import androidx.fragment.app.Fragment
import di.D

class D : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DDirections.gotoHome()
//        DArgs
    }
}