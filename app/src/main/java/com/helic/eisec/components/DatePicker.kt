package com.helic.eisec.components

import android.content.Context
import androidx.fragment.app.FragmentActivity
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.helic.eisec.R
import com.helic.eisec.utils.DateValidator
import java.util.*

fun Context?.showDatePicker(
    defaultCalendar: Calendar,
    onDismiss: (() -> Unit)? = null,
    minDate: Long = Calendar.getInstance().also {
        it.set(Calendar.MINUTE, 0)
        it.set(Calendar.HOUR_OF_DAY, 0)
        it.set(Calendar.SECOND, 0)
        it.set(Calendar.MILLISECOND, 0)
    }.timeInMillis,
    onDateSelect: (Calendar) -> Unit
) {
    (this as? FragmentActivity)?.supportFragmentManager?.let { manager ->

        val calendarConstraints = CalendarConstraints.Builder().setStart(minDate)
            .setValidator(DateValidator(minDate))
            .build()

        val builder = MaterialDatePicker.Builder.datePicker()
            .setTheme(R.style.ThemeOverlay_App_DatePicker)
            .setCalendarConstraints(calendarConstraints)
            .setSelection(defaultCalendar.timeInMillis)
            .build()

        builder.addOnPositiveButtonClickListener { selectedDate ->
            val newCal = Calendar.getInstance()
            newCal.timeInMillis = selectedDate
            onDateSelect.invoke(newCal)
        }
        builder.addOnDismissListener {
            onDismiss?.invoke()
        }
        builder.show(manager, "DatePicker")
    }
}

fun Context?.showTimePicker(
    defaultCalendar: Calendar,
    onDismiss: (() -> Unit)? = null,
    onTimeSelected: (Calendar) -> Unit
) {
    (this as? FragmentActivity)?.supportFragmentManager?.let { manager ->
        val builder = MaterialTimePicker.Builder()
            .setHour(defaultCalendar.get(Calendar.HOUR_OF_DAY))
            .setMinute(defaultCalendar.get(Calendar.MINUTE))
            .build()
        builder.addOnPositiveButtonClickListener {
            defaultCalendar.set(Calendar.HOUR_OF_DAY, builder.hour)
            defaultCalendar.set(Calendar.MINUTE, builder.minute)
            onTimeSelected.invoke(defaultCalendar)
        }
        builder.addOnDismissListener {
            onDismiss?.invoke()
        }
        builder.show(manager, "TimePicker")
    }
}
