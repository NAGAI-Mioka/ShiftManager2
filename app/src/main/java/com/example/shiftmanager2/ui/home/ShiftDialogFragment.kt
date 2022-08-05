package com.example.shiftmanager2.ui.home

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.shiftmanager2.R
import java.util.*

class ShiftDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val date = arguments?.getString("date","アイウエオ")
            val builder = AlertDialog.Builder(it)
            // Get the layout inflater
            val inflater = requireActivity().layoutInflater

            val view = View.inflate(activity, R.layout.dialog_fragment_shift, null)
            val title = view.findViewById<TextView>(R.id.shift_title)
            title.text = date

            val shiftStart = view.findViewById<EditText>(R.id.shift_start)
            shiftStart.setOnClickListener {
                // 開始時刻を押した時の動作
                Toast.makeText(context, "test", Toast.LENGTH_SHORT).show()
                val tp = TimePick()
                tp.show(childFragmentManager, "timePicker")
            }
            val shiftEnd = view.findViewById<EditText>(R.id.shift_end)
            shiftStart.setOnClickListener {
                // 終了時刻を押した時の動作

            }

            // Inflate and set the layout for the dialog
            // Pass null as the parent view because its going in the dialog layout
            builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.apply,
                    DialogInterface.OnClickListener { dialog, id ->
                        // sign in the user ...
                    })
                .setNegativeButton(R.string.cancel,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
                .setNeutralButton(R.string.delete,
                    DialogInterface.OnClickListener { dialog, id ->
                        getDialog()?.cancel()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}