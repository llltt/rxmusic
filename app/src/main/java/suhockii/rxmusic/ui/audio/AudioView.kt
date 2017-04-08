package suhockii.rxmusic.ui.audio

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/** Created by Maksim Sukhotski on 4/8/2017. */
@StateStrategyType(AddToEndSingleStrategy::class)
interface AudioView : MvpView {

//    @StateStrategyType(SkipStrategy::class)
//    fun showSnackbar(text: String)
//
//    fun showCaptcha(captcha: Captcha)
//
//    fun showValidate(validation: Validation)
//
//    fun showLogin(toString: String)
//
//    @StateStrategyType(SkipStrategy::class)
//    fun showNextController()
}