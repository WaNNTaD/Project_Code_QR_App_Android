package com.example.projectcodeqr

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ListView
import com.example.projectcodeqr.adapter.HighTechItemAdapter
import com.example.projectcodeqr.models.HighTechItem

class StudentInt : AppCompatActivity() {
    private lateinit var list_items: ListView
    val dbHelper = DBHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_int)

        //liste des items
        var highTechItemList: List<HighTechItem> = ArrayList()
        highTechItemList = dbHelper.getAllAppareils()

        //recuperer list view
        list_items = findViewById(R.id.list_items_view)
        list_items.adapter = HighTechItemAdapter(this, highTechItemList)


    }


}