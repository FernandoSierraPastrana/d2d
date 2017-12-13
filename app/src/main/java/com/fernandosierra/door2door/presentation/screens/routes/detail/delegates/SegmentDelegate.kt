package com.fernandosierra.door2door.presentation.screens.routes.detail.delegates

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.SegmentViewType
import com.fernandosierra.door2door.presentation.util.GlideApp
import io.reactivex.Single
import io.reactivex.SingleObserver

class SegmentDelegate(private val observer: SingleObserver<Int>)
    : RouteDelegate<SegmentViewType, SegmentDelegate.Companion.SegmentViewHolder> {
    companion object {
        class SegmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val imageIndicator = itemView.findViewById(R.id.image_segment_indicator) as ImageView
            val line = itemView.findViewById(R.id.view_segment_line) as View
            val imageMode = itemView.findViewById(R.id.image_segment_mode) as ImageView
            val mode = itemView.findViewById(R.id.text_segment_mode) as TextView
            val name = itemView.findViewById(R.id.text_segment_name) as TextView
            val description = itemView.findViewById(R.id.text_segment_description) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?): SegmentViewHolder =
            SegmentViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_route_segment, parent, false))

    override fun onBindViewHolder(holder: SegmentViewHolder?, viewType: SegmentViewType) {
        if (holder != null) {
            holder.imageIndicator.setColorFilter(viewType.color)
            holder.line.setBackgroundColor(viewType.color)
            holder.mode.setTextColor(viewType.color)
            holder.mode.text = viewType.travelMode.capitalize()
            if (viewType.name == null) {
                holder.name.visibility = View.GONE
            } else {
                holder.name.visibility = View.VISIBLE
                holder.name.text = viewType.name
            }
            if (viewType.description == null) {
                holder.description.visibility = View.GONE
            } else {
                holder.description.visibility = View.VISIBLE
                holder.description.text = viewType.description
            }
            holder.imageMode.setColorFilter(viewType.color)
            GlideApp.with(holder.imageMode)
                    .asSvg()
                    .load(viewType.icon)
                    .into(holder.imageMode)
            holder.itemView.setOnClickListener({ Single.just(viewType.segmentIndex).subscribe(observer) })
        }
    }
}