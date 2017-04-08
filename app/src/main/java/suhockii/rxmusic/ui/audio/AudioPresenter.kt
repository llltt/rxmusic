package suhockii.rxmusic.ui.audio

import com.arellomobile.mvp.MvpPresenter
import suhockii.rxmusic.business.preferences.PreferencesInteractor
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/8/2017. */
class AudioPresenter : MvpPresenter<AudioView>() {

    @Inject lateinit var preferencesInteractor: PreferencesInteractor
//    @Inject lateinit var preferencesInteractor: PreferencesInteractor
}