package com.hotsx.tool

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialView()
    }

    private fun initialView() {
        addBtn.setOnClickListener {
            ringView.setProgress(ringView.getProgress() + 10f)
        }
    }
}
