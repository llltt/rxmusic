package suhockii.rxmusic.ui.audio

import android.os.Bundle
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
import suhockii.rxmusic.smth.InfiniteScrollListener
import suhockii.rxmusic.ui.auth.AuthController
import suhockii.rxmusic.ui.base.MoxyController


/** Created by Maksim Sukhotski on 4/8/2017. */
class AudioController : MoxyController(), AudioView {

    @InjectPresenter
    lateinit var presenter: AudioPresenter

    override fun setupControllerComponent() {
//        App.userComponent.inject(this)
    }

    private var offset: Int = 0
    private var adapter: AudioAdapter = AudioAdapter(onClick = { presenter.playAudio(it) })

    override fun getTitle(): String = "AudioController"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_audio, container, false)
    }

    override fun onViewBound(view: View) {
        with(view) {
            val linearLayoutManager = LinearLayoutManager(activity)
            audioRecyclerView.setHasFixedSize(true)
            audioRecyclerView.layoutManager = linearLayoutManager
            audioRecyclerView.adapter = adapter
            audioRecyclerView.addOnScrollListener(InfiniteScrollListener({
                offset += 30
                presenter.getAudio(offset = offset.toString())
            }, linearLayoutManager))
        }
        presenter.validateCredentials()
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
    }

    override fun showAudio(audioResponse: AudioResponse) {
        adapter.items.addAll(audioResponse.items)
        adapter.notifyDataSetChanged()
    }

    override fun showLoginController() {
        router.setRoot(RouterTransaction.with(AuthController())
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
    }
}