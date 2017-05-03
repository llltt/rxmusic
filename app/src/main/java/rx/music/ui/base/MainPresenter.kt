package rx.music.ui.base

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import rx.music.App
import rx.music.data.network.models.Audio
import rx.music.data.repositories.preferences.PreferencesRepository
import javax.inject.Inject


/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {

    @Inject lateinit var preferencesRepository: PreferencesRepository

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        App.appComponent.inject(this)
        viewState.showContainer(isReselected = true)
    }

    fun showContainer(audioContainer: ChangeHandlerFrameLayout?, isReselected: Boolean) {
        viewState.showContainer(audioContainer!!, isReselected)
    }

    fun logout() {
        preferencesRepository.clear()
        viewState.showAuthController()
    }

    fun updatePlayer(audio: Audio) {
        viewState.showPlayer(audio)
    }

}