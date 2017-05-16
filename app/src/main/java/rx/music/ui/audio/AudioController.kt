package rx.music.ui.audio

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.controller_audio.view.*
import me.base.MoxyController
import me.extensions.toMain
import rx.music.R
import rx.music.dagger.Dagger
import rx.music.net.models.Audio


/** Created by Maksim Sukhotski on 4/8/2017. */
class AudioController : MoxyController(), AudioView {
    @ProvidePresenter fun providePresenter() = AudioPresenter(realm)

    @InjectPresenter lateinit var audioPresenter: AudioPresenter

    private lateinit var audioAdapter: AudioAdapter
    private lateinit var audioRecycler: RecyclerView

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_audio, container, false)

    override fun onViewBound(view: View) = with(view.audioRecycler) {
        val linearLayoutManager = LinearLayoutManager(activity)
        this@AudioController.audioRecycler = audioRecycler
        layoutManager = linearLayoutManager
        setHasFixedSize(true)
        addOnScrollListener(InfiniteScrollListener({
            audioPresenter.getAudio(offset = adapter.itemCount)
        }, linearLayoutManager))
    }

    override fun showRecycler(audioAdapter: AudioAdapter) = with(view!!.audioRecycler) {
        this@AudioController.audioAdapter = audioAdapter
        adapter = audioAdapter
    }

    override fun showPlayer(audio: Audio) = activity!!.toMain().mainPresenter.updatePlayer(audio)

    override fun showSelectedPos(position: Int) = audioAdapter.selectAndNotify(position)

    override fun onDestroyView(view: View) = with(view.audioRecycler) {
        super.onDestroyView(view)
        Dagger.instance.userComponent = null
        adapter = null
    }
}
