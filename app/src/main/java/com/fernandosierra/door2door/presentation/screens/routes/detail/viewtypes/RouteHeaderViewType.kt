package com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes

import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.domain.model.Route
import java.util.concurrent.TimeUnit

class RouteHeaderViewType(val providerIcon: String, val providerName: String?, type: String, durationInMillis: Long, val price: String?)
    : ViewType {
    val duration = getDurationBreakdown(durationInMillis)
    val typeStringRes = getTypeStringRes(type)

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

    private fun getTypeStringRes(@Route.Type type: String) = when (type) {
        Route.PUBLIC_TRANSPORT -> R.string.public_transport
        Route.CAR_SHARING -> R.string.car_sharing
        Route.PRIVATE_BIKE -> R.string.private_bike
        Route.BIKE_SHARING -> R.string.bike_sharing
        Route.TAXI -> R.string.taxi
        else -> R.string.empty_string
    }
}