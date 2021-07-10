package com.example.android.quakereport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_entry.*
import java.text.SimpleDateFormat

class EntryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)
        val dateTimeFormatter = SimpleDateFormat("yyyy-MM-dd")

        btnGet.setOnClickListener {
            val startDate:String = if(startTime.text.toString().isNotEmpty())  startTime.text.toString() else "2021-01-01"
            Log.d("TAG",startDate)
            val endDate:String = if(endTime.text.toString().isNotEmpty()) endTime.text.toString() else dateTimeFormatter.format(System.currentTimeMillis())
            val i = Intent(this, EarthquakeActivity::class.java)
            i.putExtra("startDate", startDate)
            i.putExtra("endDate", endDate)
            startActivity(i)
        }
    }
}