package com.qlmat.android.smartsupplier.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DateUtils {

    private const val DATE_FORMAT = "dd-MM-yyyy"

    fun formatDate(date: Long): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(Date(date))
    }

}