package com.fernandosierra.door2door.presentation.screens.routes.detail.viewtypes

import com.fernandosierra.door2door.domain.model.Segment

class SegmentViewType(val segmentIndex: Int,
                      val name: String?,
                      @Segment.Mode val travelMode: String,
                      val description: String?,
                      val color: Int,
                      val icon: String) : ViewType