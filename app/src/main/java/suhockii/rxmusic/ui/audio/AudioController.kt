package suhockii.rxmusic.ui.audio

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import kotlinx.android.synthetic.main.controller_audio.view.*
import suhockii.rxmusic.R
import suhockii.rxmusic.data.repositories.audio.models.AudioResponse
import suhockii.rxmusic.ui.base.MoxyController
import suhockii.rxmusic.ui.login.LoginController

/** Created by Maksim Sukhotski on 4/8/2017. */
class AudioController : MoxyController(), AudioView {

    @InjectPresenter
    lateinit var presenter: AudioPresenter

    override fun getTitle(): String = "AudioController"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_audio, container, false)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        return super.onCreateView(inflater, container)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
        presenter.validateCredentials()
    }

    private var adapter: AudioAdapter = AudioAdapter()

    override fun showAudio(audioResponse: AudioResponse) {
        with(view!!) {
            audioRecyclerView.layoutManager = LinearLayoutManager(activity)
            adapter = AudioAdapter(audioResponse.items, { presenter.playAudio(it) })
            audioRecyclerView.adapter = adapter
        }
    }

    override fun showLoginController() {
        router.setRoot(RouterTransaction.with(LoginController())
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
    }

}