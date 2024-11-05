package com.stylish.app.core.presentation.util

import java.text.NumberFormat
import java.util.Currency

object CurrencyFormatter {

    fun formatPrice(price: Float): String {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance()
        formatter.maximumFractionDigits = 2
        formatter.currency = Currency.getInstance("EUR")
        return formatter.format(price)
    }

}