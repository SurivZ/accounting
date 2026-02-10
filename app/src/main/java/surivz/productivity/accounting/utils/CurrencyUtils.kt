package surivz.productivity.accounting.utils

import java.text.NumberFormat
import java.util.Locale

object CurrencyUtils {

    fun toCurrency(amount: Double): String {
        return NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)
    }

    fun toDecimalString(amount: Double): String {
        return String.format(Locale.getDefault(), "%.2f", amount)
    }
}