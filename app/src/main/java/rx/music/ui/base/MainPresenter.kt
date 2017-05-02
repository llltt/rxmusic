package rx.music.ui.base

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout


/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class MainPresenter : MvpPresenter<MainView>() {
    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showContainer(isReselected = true)
    }

    fun showContainer(audioContainer: ChangeHandlerFrameLayout?, isReselected: Boolean) {
        viewState.showContainer(audioContainer!!, isReselected)
    }

}