package com.alerdoci.marvelsuperheroes.app.common.utils

import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun getWeekDaysCalender(): List<Calendar> {
    val monday = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
    }
    val tuesday = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY)
    }
    val wednesday = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY)
    }
    val thursday = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY)
    }
    val friday = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY)
    }
    val saturday = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY)
    }
    val sunday = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY)
    }

    return listOf(
        monday,
        tuesday,
        wednesday,
        thursday,
        friday,
        saturday,
        sunday,
        )
}

fun yyyyMMddToMillis(date: String): Long {
    val formatYYYY_MM_DD = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    return formatYYYY_MM_DD.parse(date)?.time ?: 0
}

fun hhMM24toAmPm(
    hh: Int, mm: Int,
): String {

    val mmTwoDigits = String.format("%02d", mm)

    return if (hh in 13..23) {
        val hhTwoDigits = String.format("%02d", hh - 12)
        "$hhTwoDigits:$mmTwoDigits PM"

    } else {
        val hhTwoDigits = if (hh == 24)
            String.format("%02d", hh - 12)
        else
            String.format("%02d", hh)

        "$hhTwoDigits:$mmTwoDigits AM"
    }
}

fun hideKeyboard(activity: Activity) {
    val imm =
        activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = activity.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(activity)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}
