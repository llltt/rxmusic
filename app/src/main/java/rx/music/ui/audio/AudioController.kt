package rx.music.ui.audio

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import kotlinx.android.synthetic.main.controller_audio.view.*
import me.base.MoxyController
import me.extensions.toMain
import rx.music.R
import rx.music.dagger.Dagger
import rx.music.net.models.Audio
import rx.music.ui.auth.AuthController


/** Created by Maksim Sukhotski on 4/8/2017. */
class AudioController : MoxyController(), AudioView {
    @InjectPresenter lateinit var audioPresenter: AudioPresenter

    private var adapter: AudioAdapter? = AudioAdapter(
            realm.where(Audio::class.java).findAllSortedAsync(Audio::pos.name),
            onClick = { audio, pos -> audioPresenter.handleAudio(realm.copyFromRealm(audio), pos) })

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View =
            inflater.inflate(R.layout.controller_audio, container, false)

    override fun onViewBound(view: View) = with(view) {
        val layoutManager = LinearLayoutManager(activity)
        audioRecycler.adapter = adapter
        audioRecycler.setHasFixedSize(true)
        audioRecycler.layoutManager = layoutManager
        audioRecycler.addOnScrollListener(InfiniteScrollListener({
            audioPresenter.getAudio(offset = adapter!!.itemCount)
        }, layoutManager))
    }

    override fun showPlayer(audio: Audio) = activity!!.toMain().mainPresenter.updatePlayer(audio)

    override fun showSelectedPos(position: Int) = adapter!!.selectAndNotify(position)

    override fun showAuthController() = router.setRoot(RouterTransaction.with(AuthController())
            .pushChangeHandler(HorizontalChangeHandler())
            .popChangeHandler(HorizontalChangeHandler()))

    override fun onDestroyView(view: View) = with(view) {
        super.onDestroyView(view)
        Dagger.instance.userComponent = null
        audioRecycler.adapter = null
    }
}
