package com.example.shiftmanager2.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shiftmanager2.databinding.FragmentHomeBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import java.time.DayOfWeek
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val calendarView: CalendarView = binding.calendarView
        calendarView.dayBinder = object : DayBinder<DayViewContainer> {
            // Called only when a new container is needed.
            override fun create(view: View) = DayViewContainer(view)

            // Called every time we need to reuse a container.
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                // この中で日ごとの表示を設定する
                container.textView.text = day.date.dayOfMonth.toString()
                container.textView.setTextColor(Color.BLACK)
                if (day.date.dayOfWeek == DayOfWeek.SUNDAY) {
                    container.textView.setTextColor(Color.RED)
                }
                if (day.date.dayOfWeek == DayOfWeek.SATURDAY) {
                    container.textView.setTextColor(Color.BLUE)
                }
                if (day.owner != DayOwner.THIS_MONTH) {
                    when (day.date.dayOfWeek) {
                        DayOfWeek.SUNDAY -> {
                            container.textView.setTextColor(Color.rgb(240, 170, 170))
                        }
                        DayOfWeek.SATURDAY -> {
                            container.textView.setTextColor(Color.rgb(140, 140, 255))
                        }
                        else -> {
                            container.textView.setTextColor(Color.GRAY)
                        }
                    }
                }
            }
        }
        calendarView.monthHeaderBinder = object : MonthHeaderFooterBinder<MonthViewContainer> {
            override fun create(view: View) = MonthViewContainer(view)
            override fun bind(container: MonthViewContainer, month: CalendarMonth) {
                container.textView.text = "${
                    month.yearMonth.month.name.lowercase(Locale.getDefault())
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
                } ${month.year}"
            }
        }
        val currentMonth = YearMonth.now()
        val lastMonth = currentMonth.plusMonths(5)
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        calendarView.setup(currentMonth, lastMonth, firstDayOfWeek)
        calendarView.scrollToMonth(currentMonth)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}