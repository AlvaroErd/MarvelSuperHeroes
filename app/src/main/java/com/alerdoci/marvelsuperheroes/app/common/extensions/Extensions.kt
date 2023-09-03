package com.alerdoci.marvelsuperheroes.app.common.extensions

import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale

object Extensions {

    fun String.capitalized(): String {
        return lowercase().replaceFirstChar { it.titlecase() }
    }

    suspend fun <T> runIo(
        function: suspend () -> T
    ): T = withContext(Dispatchers.IO) { function() }

    fun String.toToast(context: Context, length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, this, length).show()
    }

    fun Context.getActivity(): AppCompatActivity? = when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

    fun Int.formatVoteCount(): String {
        val df = DecimalFormat("#.#")
        return if (this < 1000) {
            "$this"
        } else {
            val formattedCount = if (this in 1000..999999) {
                df.format((this / 1000.0)).toString().replace(',', '.')
            } else {
                df.format((this / 1000000.0)).toString().replace(',', '.')
            }
            "$formattedCount${if (this in 1000..999999) "k" else "M"}"
        }
    }

    fun <T> Boolean.iif(ifTrue: T, ifFalse: T): T = if (this) ifTrue else ifFalse
    fun Int.format(format: String = "#,##0"): String = if (this >= 0) DecimalFormat(
        format,
        DecimalFormatSymbols().also { x -> x.decimalSeparator = ',' }.also { x ->
            x.groupingSeparator = '.'
        }).format(this) else DecimalFormat("${format}-").format(this)

    fun Long.format(format: String = "#,##0"): String = DecimalFormat(
        format,
        DecimalFormatSymbols().also { x -> x.decimalSeparator = ',' }
            .also { x -> x.groupingSeparator = '.' }).format(this)

    fun Double.format(format: String = "#,##0.00"): String = DecimalFormat(format,
        DecimalFormatSymbols().also { x -> x.decimalSeparator = ',' }
            .also { x -> x.groupingSeparator = '.' }).format(this)

    fun Float.format(format: String = "#,##0.00"): String = DecimalFormat(format,
        DecimalFormatSymbols().also { x -> x.decimalSeparator = ',' }
            .also { x -> x.groupingSeparator = '.' }).format(this)

    fun Date?.format(format: String = "dd-MM-yyyy"): String =
        if (this != null) SimpleDateFormat(format).format(this) else ""

    fun CharSequence?.toDate(format: String = "dd-MM-yyyy"): Date? = try {
        SimpleDateFormat(format).parse(this.toString())
    } catch (ex: Exception) {
        null
    }

    fun Calendar.today(): Date = SimpleDateFormat(
        "dd-MM-yyyy",
        Locale.getDefault()
    ).parse(this.time.format("dd-MM-yyyy")) as Date

    fun CharSequence?.toNotNullDate(format: String = "dd-MM-yyyy"): Date = try {
        this?.let { date -> SimpleDateFormat(format).parse(date.toString()) }
            ?: Calendar.getInstance().time.format(format).toDate()!!
    } catch (ex: Exception) {
        Calendar.getInstance().time.format(format).toDate()!!
    }

    fun String?.toDate(format: String = "dd-MM-yyyy"): Date? = try {
        this?.let { SimpleDateFormat(format).parse(it) }
    } catch (ex: Exception) {
        null
    }

    fun String?.toNotNullDate(format: String = "dd-MM-yyyy"): Date = try {
        this?.let { date -> SimpleDateFormat(format).parse(date) }
            ?: Calendar.getInstance().time.format(format).toDate()!!
    } catch (ex: Exception) {
        Calendar.getInstance().time.format(format).toDate()!!
    }

    infix fun Date.plusDays(days: Int): Date = Calendar.getInstance().apply {
        time = this@plusDays
        add(Calendar.DAY_OF_MONTH, days)
        add(Calendar.SECOND, 86399)
    }.time

    infix fun Date.plusMonth(month: Int): Date = Calendar.getInstance().apply {
        time = this@plusMonth
        add(Calendar.MONTH, month)
    }.time

    fun Date.getMondayOfWeek(): Date = Calendar.getInstance().apply {
        time = this@getMondayOfWeek
        add(Calendar.DAY_OF_WEEK, this.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY)
    }.time

    fun Date.getFirstDayOfMonth(): Date = Calendar.getInstance().apply {
        time = this@getFirstDayOfMonth
        set(Calendar.DAY_OF_MONTH, 1)
    }.time

    fun Date.getLastDayOfMonth(): Date = (this.getFirstDayOfMonth() plusMonth 1) plusDays -1

    fun RangeDateFilter.getDateRange(): Pair<Date, Date> {
        val today = Calendar.getInstance().today()

        return when (this) {
            RangeDateFilter.Daily -> Pair(today, today plusDays 0)
            RangeDateFilter.Weekly -> Pair(
                today.getMondayOfWeek(),
                (today.getMondayOfWeek() plusDays 6)
            )

            RangeDateFilter.Biweekly -> Pair(
                today.getMondayOfWeek(),
                (today.getMondayOfWeek() plusDays 14)
            )

            RangeDateFilter.Monthly -> Pair(today.getFirstDayOfMonth(), today.getLastDayOfMonth())
        }
    }

    enum class RangeDateFilter(val value: Int) {
        Daily(0),
        Weekly(1),
        Biweekly(2),
        Monthly(3)
    }

    fun <T>Boolean.iif(ifTrue: () -> T, ifFalse: () -> Unit): Any? = if (this) ifTrue() else ifFalse()

    fun String?.toInstant(format: String = "dd-MM-yyyy"): Instant? = try{
        this?.let { LocalDateTime.parse(it, DateTimeFormatter.ofPattern(format, Locale.getDefault())).toInstant(
            ZoneOffset.UTC) }
    }catch (ex: Exception){
        null
    }

    infix fun Instant.plusDays(days: Long): Instant = this.plus(days , ChronoUnit.DAYS)

    infix fun Instant.plusMonth(month: Long): Instant = this.plus(month , ChronoUnit.MONTHS)

    fun Instant.getMondayOfWeek(): Instant = this.plus((DayOfWeek.MONDAY.value - this.atZone(
        ZoneOffset.UTC).dayOfWeek.value).toLong() , ChronoUnit.DAYS)


    fun Instant.getFirstDayOfMonth(): Instant = this.atZone(ZoneOffset.UTC).withDayOfMonth(1).toInstant()
}
