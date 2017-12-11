package com.fernandosierra.door2door.presentation.screens.routes.detail.delegates

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes.ViewType

interface RouteDelegate<in VT : ViewType, VH : RecyclerView.ViewHolder> {

    fun onCreateViewHolder(parent: ViewGroup?): VH

    fun onBindViewHolder(holder: VH?, viewType: VT)
}