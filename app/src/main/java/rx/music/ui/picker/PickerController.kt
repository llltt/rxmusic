package rx.music.ui.picker

import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.controller_audio.view.*
import kotlinx.android.synthetic.main.controller_auth.view.*
import kotlinx.android.synthetic.main.controller_picker.view.*
import me.base.MoxyController
import me.extensions.onClick
import me.extensions.toMain
import rx.music.R
import rx.music.net.models.audio.Audio
import rx.music.ui.audio.AudioAdapter
import rx.music.ui.audio.AudioPresenter
import rx.music.ui.audio.AudioView
import rx.music.ui.audio.InfiniteScrollListener

/** Created by Maksim Sukhotski on 5/6/2017. */
class PickerController : MoxyController(), AudioView {
    @ProvidePresenter fun providePresenter() = AudioPresenter(realm)
    @InjectPresenter lateinit var audioPresenter: AudioPresenter

    private lateinit var audioAdapter: AudioAdapter
    private lateinit var audioRecycler: RecyclerView

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_picker, container, false)
    }

    override fun onViewBound(view: View) = with(view.pickerRecycler) {
        val linearLayoutManager = LinearLayoutManager(activity)
        this@PickerController.audioRecycler = audioRecycler
        layoutManager = linearLayoutManager
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()
        adapter = audioAdapter
        setHasFixedSize(true)
        addOnScrollListener(InfiniteScrollListener({
            audioPresenter.getAudio(offset = adapter.itemCount)
        }, linearLayoutManager))
        dialogContainer.onClick {
            handleBack()
            router.popCurrentController()
        }
    }

    override fun showRecycler(audioAdapter: AudioAdapter) = with(view!!.audioRecycler) {
        this@PickerController.audioAdapter = audioAdapter
        adapter = audioAdapter
    }

    override fun handleBack(): Boolean {
        if (!activity!!.toMain().isAnimate) {
            activity!!.toMain().isAnimate = true
            view?.postDelayed({
                activity!!.toMain().resetSlidingPanel()
            }, 200)
            return super.handleBack()
        }
        return false
    }

    override fun showSnackbar(text: String) = with(view!!) {
        Snackbar.make(loginLayout, text, Snackbar.LENGTH_LONG).show()
    }

    override fun showPlayer(audio: Audio) = activity!!.toMain().mainPresenter.updatePlayer(audio)

    override fun showSelectedPos(position: Int) = audioAdapter.selectAndNotify(position)
}