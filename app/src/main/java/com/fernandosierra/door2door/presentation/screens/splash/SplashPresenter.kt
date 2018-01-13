package com.fernandosierra.door2door.presentation.screens.splash

import com.fernandosierra.door2door.domain.interactor.SetupInteractor
import com.fernandosierra.door2door.presentation.base.BasePresenter
import com.fernandosierra.door2door.presentation.util.readJsonAsset
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch
import javax.inject.Inject

class SplashPresenter @Inject constructor(view: SplashView, private val setupInteractor: SetupInteractor)
    : BasePresenter<SplashView>(view) {
    companion object {
        private const val LOCAL_JSON = "local.json"
    }

    fun init() = view { it.init() }

    fun loadLocalData() {
        view { it.showLoader() }

        launch(UI) {
            val deferred = async {
                var localDataJson: String? = null
                view { localDataJson = it.ctx?.readJsonAsset(LOCAL_JSON) }
                if (localDataJson != null) {
                    setupInteractor.setupFromLocalData(localDataJson ?: "")
                } else {
                    throw RuntimeException("Couldn't load data from local JSON")
                }
            }
            deferred.join()
            if (deferred.getCompletionExceptionOrNull() == null) {
                view { it.goRoutes() }
            } else {
                view { it.showErrorDialog() }
            }
        }
    }
}