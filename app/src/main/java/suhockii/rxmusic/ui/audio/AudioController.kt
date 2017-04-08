package suhockii.rxmusic.ui.audio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import suhockii.rxmusic.R
import suhockii.rxmusic.ui.base.MoxyController

/** Created by Maksim Sukhotski on 4/8/2017. */
class AudioController : MoxyController(), AudioView {

    @InjectPresenter
    lateinit var presenter: AudioPresenter

    override fun getTitle(): String = "AudioController"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_audio, container, false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter
    }
}