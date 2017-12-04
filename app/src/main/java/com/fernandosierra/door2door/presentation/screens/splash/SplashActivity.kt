package com.fernandosierra.door2door.presentation.screens.splash

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import com.fernandosierra.door2door.R
import com.fernandosierra.door2door.presentation.custom.LoaderView
import com.fernandosierra.door2door.presentation.screens.home.HomeActivity
import com.fernandosierra.door2door.presentation.util.launchActivity
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
        loaderView.playAnimation()
    }

    override fun hideLoader(onAnimationEnd: Runnable?) {
        loaderView.cancelAnimation(onAnimationEnd)
    }

    override fun showErrorDialog() {
        AlertDialog.Builder(this)
                .setTitle(R.string.title_splash_retry_dialog)
                .setMessage(R.string.message_splash_retry_dialog)
                .setPositiveButton(R.string.retry_splash_retry_dialog, { dialog, _ ->
                    dialog.dismiss()
                    presenter.loadLocalData()
                })
                .setNegativeButton(R.string.cancel_splash_retry_dialog, { dialog, _ ->
                    dialog.dismiss()
                    finish()
                })
                .setCancelable(false)
                .show()
    }

    override fun goHome() {
        launchActivity<HomeActivity>()
        finish()
    }
}
