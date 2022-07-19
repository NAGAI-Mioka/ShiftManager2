package com.example.shiftmanager2.ui.home

import android.view.View
import android.widget.TextView
import com.example.shiftmanager2.R
import com.kizitonwose.calendarview.ui.ViewContainer

class MonthViewContainer(view: View) : ViewContainer(view) {
    val textView: TextView = view.findViewById<TextView>(R.id.headerTextView)
}