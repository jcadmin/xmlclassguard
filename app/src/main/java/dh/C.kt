package dh

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ljx.example.R

class C : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun test(main: dh.C) {
        Log.d("LJX", main::class.java.name)
    }
}