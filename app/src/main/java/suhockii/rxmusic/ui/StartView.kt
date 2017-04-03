package suhockii.rxmusic.ui

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/** Created by Maksim Sukhotski on 4/1/2017.*/
@StateStrategyType(AddToEndSingleStrategy::class)
interface StartView : MvpView {

    fun showSnackbar(text: String)
    fun clean()
}