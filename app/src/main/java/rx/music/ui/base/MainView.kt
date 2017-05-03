package rx.music.ui.base

import rx.music.data.network.models.Audio

/** Created by Maksim Sukhotski on 5/1/2017. */
@com.arellomobile.mvp.viewstate.strategy.StateStrategyType(com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy::class)
interface MainView : com.arellomobile.mvp.MvpView {
    fun showContainer(container: com.bluelinelabs.conductor.ChangeHandlerFrameLayout? = null, isReselected: Boolean)
    fun showAuthController()
    fun showPlayer(audio: Audio)
}