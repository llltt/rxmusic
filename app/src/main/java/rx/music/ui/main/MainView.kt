package rx.music.ui.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout

/** Created by Maksim Sukhotski on 5/1/2017. */
@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView : MvpView {
    fun showContainer(container: ChangeHandlerFrameLayout? = null, isReselected: Boolean)
    fun showAuthController()
    fun showOnAuthorized(isAfterAuth: Boolean)
    fun showPlayListScreen()
}