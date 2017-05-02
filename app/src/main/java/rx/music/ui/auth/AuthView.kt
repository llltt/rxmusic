package rx.music.ui.auth

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import rx.music.data.net.models.Captcha
import rx.music.data.net.models.Validation

/** Created by Maksim Sukhotski on 4/1/2017.*/
@StateStrategyType(AddToEndSingleStrategy::class)
interface AuthView : MvpView {

    @StateStrategyType(SkipStrategy::class)
    fun showSnackbar(text: String)

    fun showCaptcha(captcha: Captcha)

    fun showValidation(validation: Validation)

    fun showLogin(string: Any)

    @StateStrategyType(SkipStrategy::class)
    fun showAudioController()

    fun hideNavigation()
}