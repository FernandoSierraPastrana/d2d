package com.fernandosierra.door2door.presentation.screens.routes.detail.delegates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.StopViewType

class StopDelegate : RouteDelegate<StopViewType, StopDelegate.Companion.StopViewHolder> {
    companion object {
        class StopViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageIndicator = itemView.findViewById(R.id.image_stop_indicator) as ImageView
            val line = itemView.findViewById(R.id.view_stop_line) as View
            val name = itemView.findViewById(R.id.text_stop_name) as TextView
            val date = itemView.findViewById(R.id.text_stop_date) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): StopViewHolder =
            StopViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_route_stop, parent, false))

    override fun onBindViewHolder(holder: StopViewHolder?, viewType: StopViewType) {
        if (holder != null) {
            if (viewType.isLast) {
                holder.imageIndicator.setImageResource(R.drawable.ic_segment)
            } else {
                holder.imageIndicator.setImageResource(R.drawable.ic_stop)
            }
            holder.imageIndicator.setColorFilter(viewType.color)
            holder.line.setBackgroundColor(viewType.color)
            holder.name.text = viewType.name
            holder.date.text = viewType.date
        }
    }
}