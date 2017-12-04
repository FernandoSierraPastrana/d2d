package com.fernandosierra.door2door.presentation.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.presentation.custom.LoaderView
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import javax.inject.Inject

class SplashActivity : AppCompatActivity(), SplashView {
    @Inject
    lateinit var presenter: SplashPresenter
    lateinit var loaderView: LoaderView

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        presenter.init()
    }

    override fun onResume() {
        super.onResume()
        presenter.loadLocalData()
    }

    override fun init() {
        loaderView = lottie_splash_loader
    }

    override fun showLoader() {
        loaderView.visibility = View.VISIBLE
        loaderView.playAnimation()
    }

    override fun hideLoader() {
        loaderView.cancelAnimation({ loaderView.visibility = View.GONE })
    }
}
