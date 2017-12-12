package com.fernandosierra.door2door.presentation.screens.routes.detail.delegates

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.SegmentViewType
import com.fernandosierra.door2door.presentation.util.svg.GlideSvg

class SegmentDelegate : RouteDelegate<SegmentViewType, SegmentDelegate.Companion.SegmentViewHolder> {
    companion object {
        class SegmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageIndicator = itemView.findViewById(R.id.image_segment_indicator) as ImageView
            val line = itemView.findViewById(R.id.view_segment_line) as View
            val imageMode = itemView.findViewById(R.id.image_segment_mode) as ImageView
            val name = itemView.findViewById(R.id.text_segment_name) as TextView
            val description = itemView.findViewById(R.id.text_segment_description) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SegmentViewHolder =
            SegmentViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_route_segment, parent, false))

    override fun onBindViewHolder(holder: SegmentViewHolder?, viewType: SegmentViewType) {
        if (holder != null) {
            val color = Color.parseColor(viewType.color)
            holder.imageIndicator.setColorFilter(color)
            holder.line.setBackgroundColor(color)
            holder.name.text = viewType.name
            holder.description.text = viewType.description
            GlideSvg.loadInto(viewType.icon, holder.imageMode)
        }
    }
}