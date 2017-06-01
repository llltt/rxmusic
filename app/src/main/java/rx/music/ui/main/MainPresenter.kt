package rx.music.ui.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import rx.music.App
import rx.music.data.preferences.PreferencesRepo
import javax.inject.Inject


/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var preferencesRepo: PreferencesRepo


    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.appComponent.inject(this)
        viewState.showContainer(isReselected = true)
        if (preferencesRepo.isAuthorized) viewState.showOnAuthorized(false)
        else viewState.showAuthController()
    }

    fun showContainer(audioContainer: ChangeHandlerFrameLayout?, isReselected: Boolean) =
        viewState.showContainer(audioContainer!!, isReselected)

    fun logout() {
        preferencesRepo.clear()
        viewState.showContainer(isReselected = true)
        viewState.showAuthController()
    }
}
