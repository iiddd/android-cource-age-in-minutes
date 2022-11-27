package com.iiddd.ageinminutes

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private var tvSelectedDate: TextView? = null
    private var tvAgeInMinutes: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tvSelectedDate = findViewById(R.id.tvSelectedDate)
        tvAgeInMinutes = findViewById(R.id.tvAgeInMinutes)
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            clickDatePicker()
        }
    }

    private fun clickDatePicker() {
        val myCalendar: Calendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                Toast.makeText(
                    this,
                    "Day is $selectedDayOfMonth, Month is $selectedMonth, Year is $selectedYear",
                    Toast.LENGTH_LONG
                ).show()
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
                tvSelectedDate?.text = selectedDate
                tvAgeInMinutes?.text = getTimeInMinutes(selectedDate).toString()
            },
            year,
            month,
            day
        )
        datePickerDialog.datePicker.maxDate = Date().time - millisInDay
        datePickerDialog.show()
    }

    private fun getTimeInMinutes(selectedDate: String): Long {
        val sdf = SimpleDateFormat(dateFormatPattern, Locale.ENGLISH)
        val convertedDate = sdf.parse(selectedDate)
        var timePassed: Long = 0
        convertedDate?.let {
            timePassed = Date().time - convertedDate.time
        }
        return timePassed / 60000
    }

    companion object {
        const val dateFormatPattern = "dd/MM/yyyy"
        const val millisInDay = 86400000
    }
}