package com.example.shiftmanager2.ui.home

import android.view.View
import android.widget.TextView
import com.example.shiftmanager2.R
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.ui.ViewContainer

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView: TextView = view.findViewById<TextView>(R.id.calendarDayText)

    lateinit var day: CalendarDay

    init {
        view.setOnClickListener {
            // Use the CalendarDay associated with this container.
        }
    }
    // With ViewBinding
    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}