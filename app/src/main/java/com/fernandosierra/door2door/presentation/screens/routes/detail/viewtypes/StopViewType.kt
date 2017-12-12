package com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes

import java.text.SimpleDateFormat
import java.util.*

class StopViewType(val name: String?, dateInMillis: Long, val color: Int, val isLast: Boolean) : ViewType {
    val date = parseDate(dateInMillis)

    companion object {
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    }

    private fun parseDate(dateInMillis: Long) =
            if (dateInMillis == 0L) {
                null
            } else {
                dateFormat.format(Date(dateInMillis))
            }
}