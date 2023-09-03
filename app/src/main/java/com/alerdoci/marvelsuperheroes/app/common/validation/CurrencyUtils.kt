package com.alerdoci.marvelsuperheroes.app.common.validation

import android.util.Log
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.Locale


object CurrencyUtils {

    private const val MAXIMUM_CURRENCY_FRACTION_DIGITS = 2

    fun formatToCurrencyWithSymbol(amount: Double, currency: String): String {
        val nf = NumberFormat.getCurrencyInstance()
        val decimalFormatSymbols = (nf as DecimalFormat).decimalFormatSymbols
        decimalFormatSymbols.currencySymbol = ""
        nf.setMaximumFractionDigits(MAXIMUM_CURRENCY_FRACTION_DIGITS)
        nf.decimalFormatSymbols = decimalFormatSymbols
        nf.setRoundingMode(RoundingMode.HALF_UP)
        return if (currency.isEmpty()) {
            nf.format(amount)
        } else String.format("%s %s", currency, nf.format(amount))
    }

    fun parseAmount(amountAsText: String): Double {
        var amountString = amountAsText
        val nf = DecimalFormat.getInstance(Locale.getDefault())

        val groupingSeparator = (nf as DecimalFormat).decimalFormatSymbols.groupingSeparator
        amountString = amountString.replace(groupingSeparator.toString().toRegex(), "")
        try {
            nf.parse(amountString)?.let {
                return it.toDouble()
            }
        } catch (e: ParseException) {
            Log.d("Exception: %s", e.toString())
        }

        Log.d("Exception: %s","Returning 0, it seems something happened.")
        return 0.0
    }
}
