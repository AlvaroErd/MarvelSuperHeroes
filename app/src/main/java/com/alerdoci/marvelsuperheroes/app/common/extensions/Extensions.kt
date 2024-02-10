package com.alerdoci.marvelsuperheroes.app.common.extensions

import android.content.Context
import android.content.ContextWrapper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.HTTP
import com.alerdoci.marvelsuperheroes.domain.constants.Constants.Companion.HTTPS
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

// Kotlin Extensions
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

    fun formatNumber(number: Int): String {
        return when {
            number < 1000 -> number.toString()
            number < 1000000 -> {
                val decimalValue = number / 100 % 10
                val formatted = DecimalFormat("#.0").format(number.toFloat() / 1000)
                if (decimalValue == 0) {
                    (number / 1000).toString() + "k"
                } else {
                    formatted + "k"
                }
            }
            else -> "${number / 1000000}M"
        }
    }

    /*
        val currentTime = System.currentTimeMillis()
        val fakeNumberInSeconds = "2023-11-01"

        val newsTime = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(fakeNumberInSeconds)?.time

        val diff = currentTime - newsTime!!

        if (diff < 60000) { // Less than 1 minute
            holder.subtitle.text = holder.itemView.context.getString(R.string.time_ago_seconds)
        } else if (diff < 3600000) { // Less than 1 hour
            val minutes = diff / 60000
            holder.subtitle.text = holder.itemView.context.getString(R.string.time_ago_minutes, minutes)
        } else if (diff < 86400000) { // Less than 1 day
            val hours = diff / 3600000
            if (hours == 1L) {
                holder.subtitle.text = holder.itemView.context.getString(R.string.time_ago_hour)
            } else {
                val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(lastNewsList.attributes.emision.date)
                val yesterday = Calendar.getInstance().apply {
                    add(Calendar.DATE, -1)
                }.time
                val isYesterday = date?.after(yesterday) ?: false
                if (isYesterday) {
                    val dateFormat = SimpleDateFormat("HH:mm")
                    val formattedDate = holder.itemView.context.getString(R.string.time_yesterday) + dateFormat.format(date!!)
                    holder.subtitle.text = formattedDate
                } else {
                    holder.subtitle.text = holder.itemView.context.getString(R.string.time_ago_hours, hours)
                }
            }
        } else if (diff < 172800000) { // Less than 2 days
            val dateFormat = SimpleDateFormat("HH:mm")
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(lastNewsList.attributes.emision.date)
            val calendar = Calendar.getInstance().apply { timeInMillis = newsTime }
            val today = Calendar.getInstance()
            val formattedDate = if (calendar.get(Calendar.DAY_OF_YEAR) == today.get(Calendar.DAY_OF_YEAR) - 1
                && calendar.get(Calendar.YEAR) == today.get(Calendar.YEAR)) {
                holder.itemView.context.getString(R.string.time_yesterday) + dateFormat.format(date!!)
            } else {
                holder.itemView.context.getString(R.string.the_day_before_yesterday) + dateFormat.format(date!!)
            }
            holder.subtitle.text = formattedDate
        } else if (diff < 259200000) { // Less than 3 days
            val dateFormat = SimpleDateFormat("HH:mm")
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(lastNewsList.attributes.emision.date)
            val yesterday = Calendar.getInstance()
            yesterday.add(Calendar.DAY_OF_MONTH, -1)
            if (date!!.before(yesterday.time)) {
                val dateFormatEee = SimpleDateFormat("EEEE, HH:mm")
                val formattedDate = dateFormatEee.format(date!!)
                holder.subtitle.text = formattedDate.replaceFirstChar { it.uppercase() }
            } else {
                val formattedDate = holder.itemView.context.getString(R.string.time_yesterday) + dateFormat.format(date)
                holder.subtitle.text = formattedDate
            }
        } else { // More than 3 days
            val dateFormat = SimpleDateFormat("EEEE, HH:mm")
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX").parse(lastNewsList.attributes.emision.date)
            val formattedDate = dateFormat.format(date!!)
            holder.subtitle.text = formattedDate.replaceFirstChar { it.uppercase() }
        }

        <string name="time_ago_seconds">Hace unos segundos</string>
        <string name="time_ago_minutes">Hace %1$d minutos</string>
        <string name="time_ago_hour">Hace 1 hora</string>
        <string name="time_ago_hours">Hace %1$d horas</string>
        <string name="time_yesterday">"Ayer, "</string>
        <string name="the_day_before_yesterday">"Anteayer, "</string>*/

    fun String.replaceHttp(): String {
        val http = HTTP
        val https = HTTPS
        return this.replace(http, https)
    }

    fun getYear(date: Date): Int{
        return date.year+1900
    }

    fun getNameOfMonthByNumber(monthNumber: Int, context: Context): String {
        return when(monthNumber) {
            0 -> "Enero"//context.resources.getString(R.string.january)
            1 -> "Febrero"//context.resources.getString(R.string.february)
            2 -> "Marzo"//context.resources.getString(R.string.march)
            3 -> "Abril"//context.resources.getString(R.string.april)
            4 -> "Mayo"//context.resources.getString(R.string.may)
            5 -> "Junio"//context.resources.getString(R.string.june)
            6 -> "Julio"//context.resources.getString(R.string.july)
            7 -> "Agosto"//context.resources.getString(R.string.august)
            8 -> "Septiembre"//context.resources.getString(R.string.september)
            9 -> "Octubre"//context.resources.getString(R.string.october)
            10-> "Noviembre"//context.resources.getString(R.string.november)
            else -> "Diciembre"//context.resources.getString(R.string.december)
        }
    }

    fun getNameOfDayByDate(date: Date, context: Context): String {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return when(calendar[Calendar.DAY_OF_WEEK]) {
            1 -> "Lunes"//MainActivity.instance!!.applicationContext.getString(R.string.sunday)
            2 -> "Martes"//context.resources.getString(R.string.monday)
            3 -> "Miercoles"//context.resources.getString(R.string.tuesday)
            4 -> "Jueves"//context.resources.getString(R.string.wednesday)
            5 -> "Viernes"//context.resources.getString(R.string.thursday)
            6 -> "Sabado"//context.resources.getString(R.string.friday)
            else -> "Domingo"//context.resources.getString(R.string.saturday)
        }
    }
}
