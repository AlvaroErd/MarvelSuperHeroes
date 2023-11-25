package com.alerdoci.marvelsuperheroes.app.common.utils

import android.app.Activity
import android.content.Intent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
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

fun Date.toStringFormatted(): String {
    val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    return outputDateFormat.format(this)
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

fun shareContent(view: View) {
    var currentImageUrl: String? = null
    val intent = Intent(Intent.ACTION_SEND)
    intent.type ="text/plain"
    intent.putExtra(Intent.EXTRA_TEXT,"Hey! Checkout this cool content. $currentImageUrl")
    val chooser = Intent.createChooser(intent,"Share this using...")
//    startActivity(chooser)
}

suspend fun <T> ListIterator<T>.doWhenHasNextOrPrevious(
    delayMills: Long = 3000,
    doWork: suspend (T) -> Unit
) {
    while (hasNext() || hasPrevious()) {
        while (hasNext()) {
            delay(delayMills)
            doWork(next())
        }
        while (hasPrevious()) {
            delay(delayMills)
            doWork(previous())
        }
    }
}

object ScrollAnimation {
    @OptIn(ExperimentalAnimationApi::class)
    operator fun invoke(): ContentTransform {
        return (slideInVertically(
            initialOffsetY = { 50 },
            animationSpec = tween()
        ) + fadeIn()).togetherWith(
            slideOutVertically(
                targetOffsetY = { -50 },
                animationSpec = tween()
            ) + fadeOut()
        )
    }
}

/**
 * This prevents text size from enlarging when
 * user increases their devices' font size
 */

@Composable
fun Int.withScale() {
    return with(LocalDensity.current) {
        (this@withScale / fontScale).sp
    }
}
