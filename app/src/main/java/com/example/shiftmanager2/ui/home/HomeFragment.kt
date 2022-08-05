package com.example.shiftmanager2.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.shiftmanager2.databinding.FragmentHomeBinding
import com.kizitonwose.calendarview.CalendarView
import com.kizitonwose.calendarview.model.CalendarDay
import com.kizitonwose.calendarview.model.CalendarMonth
import com.kizitonwose.calendarview.model.DayOwner
import com.kizitonwose.calendarview.ui.DayBinder
import com.kizitonwose.calendarview.ui.MonthHeaderFooterBinder
import com.kizitonwose.calendarview.utils.yearMonth
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.temporal.WeekFields
import java.util.*

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var selectedDate: LocalDate? = null

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
            // 日付ごとの処理
            override fun bind(container: DayViewContainer, day: CalendarDay) {
                container.day = day
                // 日付がクリックされた時の処理
                container.view.setOnClickListener {
                    // ダイアログ生成
                    val newFragment = ShiftDialogFragment()
                    // バンドル作成＆バンドルをアーグメントに与える
                    val args = Bundle()
                    args.putString("date", day.date.toString())
                    newFragment.arguments = args
                    // 表示
                    newFragment.show(childFragmentManager, "game")

                    val currentSelection = selectedDate
                    if (currentSelection == day.date) {
                        // If the user clicks the same date, clear selection.
                        selectedDate = null
                        // Reload this date so the dayBinder is called
                        // and we can REMOVE the selection background.
                        calendarView.notifyMonthChanged(day.date.yearMonth.plusMonths(1))
                        calendarView.notifyMonthChanged(day.date.yearMonth)
                        calendarView.notifyMonthChanged(day.date.yearMonth.minusMonths(1))
                    } else {
                        selectedDate = day.date
                        // Reload the newly selected date so the dayBinder is
                        // called and we can ADD the selection background.

                        calendarView.notifyMonthChanged(day.date.yearMonth.plusMonths(1))
                        calendarView.notifyMonthChanged(day.date.yearMonth)
                        calendarView.notifyMonthChanged(day.date.yearMonth.minusMonths(1))
                        if (currentSelection != null) {
                            // We need to also reload the previously selected
                            // date so we can REMOVE the selection background.
                            calendarView.notifyMonthChanged(currentSelection.yearMonth.plusMonths(1))
                            calendarView.notifyMonthChanged(currentSelection.yearMonth)
                            calendarView.notifyMonthChanged(currentSelection.yearMonth.minusMonths(1))
                        }
                    }
                }

                container.textView.text = day.date.dayOfMonth.toString()
                // 今月の日付について
                if (day.owner == DayOwner.THIS_MONTH) {
                    // 基本の文字色
                    when (day.date.dayOfWeek) {
                        DayOfWeek.SUNDAY -> {
                            container.textView.setTextColor(Color.RED)
                        }
                        DayOfWeek.SATURDAY -> {
                            container.textView.setTextColor(Color.BLUE)
                        }
                        else -> {
                            container.textView.setTextColor(Color.BLACK)
                        }
                    }
                // 今月以外の日付について
                } else {
                    // 基本の文字色
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

                // 選択された日について
                if (day.date == selectedDate) {
                    // If this is the selected date, show a round background and change the text color.
                    container.textView.setBackgroundColor(Color.YELLOW)

                    //container.textView.setBackgroundResource(R.drawable.selection_background)
                } else {
                    container.textView.background = null
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