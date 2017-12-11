package com.fernandosierra.door2door.presentation.screens.routes.detail.delegates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.RouteHeaderViewType

class RouteHeaderDelegate : RouteDelegate<RouteHeaderViewType, RouteHeaderDelegate.Companion.HeaderViewHolder> {
    companion object {
        class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val providerImage = itemView.findViewById(R.id.image_route_provider) as ImageView
            val provider = itemView.findViewById(R.id.text_route_provider) as TextView
            val duration = itemView.findViewById(R.id.text_route_duration) as TextView
            val price = itemView.findViewById(R.id.text_route_price) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): HeaderViewHolder =
            HeaderViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_route_header, parent, false))

    override fun onBindViewHolder(holder: HeaderViewHolder?, viewType: RouteHeaderViewType) {
        if (holder != null) {
            val providerString = when (viewType.providerName) {
                null -> null
                else -> String.format(holder.provider.context.getString(R.string.provider_template), viewType.providerName)
            }
            holder.provider.text = providerString
            holder.price.text = viewType.price
            holder.duration.text = viewType.duration
            Glide.with(holder.providerImage)
                    .load(viewType.providerIcon)
                    .into(holder.providerImage)
        }
    }
}