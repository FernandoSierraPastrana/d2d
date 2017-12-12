package com.fernandosierra.door2door.presentation.screens.routes.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.domain.model.Route
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.fragment_route.*
import javax.inject.Inject

class RouteFragment : Fragment(), RouteView {
    private lateinit var route: Route
    private lateinit var recyclerView: RecyclerView
    private val routeAdapter = RouteAdapter()
    @Inject
    lateinit var presenter: RoutePresenter

    companion object {
        private const val EXTRA_ROUTE = "EXTRA_ROUTE"

        fun newInstance(route: Route): RouteFragment {
            val instance = RouteFragment()
            val arguments = Bundle()
            arguments.putParcelable(EXTRA_ROUTE, route)
            instance.arguments = arguments
            return instance
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
        return inflater.inflate(R.layout.fragment_route, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.init()
    }

    override fun init() {
        route = arguments!!.getParcelable(EXTRA_ROUTE)
        recyclerView = recycler_route
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = routeAdapter
        routeAdapter.setRoute(route)
        routeAdapter.notifyDataSetChanged()
    }

    fun reset() {
        recyclerView.scrollToPosition(0)
    }
}