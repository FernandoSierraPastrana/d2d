package com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes

import java.util.concurrent.TimeUnit

class RouteHeaderViewType(val providerIcon: String, val providerName: String?, durationInMillis: Long, val price: String?) : ViewType {
    val duration = getDurationBreakdown(durationInMillis)

    companion object {
        private const val HOURS = " hrs "
        private const val MINUTES = " min "
    }

    private fun getDurationBreakdown(duration: Long): String {
        var millis = duration
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        millis -= TimeUnit.HOURS.toMillis(hours)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
        millis -= TimeUnit.MINUTES.toMillis(minutes)
        val builder = StringBuilder()
        if (hours > 0) {
            builder.append(hours)
            builder.append(HOURS)
        }
        builder.append(minutes)
        builder.append(MINUTES)
        return builder.toString()
    }
}