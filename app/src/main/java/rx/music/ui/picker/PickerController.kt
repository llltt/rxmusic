package rx.music.ui.picker

import android.support.v7.widget.LinearLayoutManager
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.controller_picker.view.*
import kotlinx.android.synthetic.main.part_containers.*
import me.base.MoxyController
import me.extensions.onClick
import rx.music.R
import rx.music.net.models.Audio
import rx.music.net.models.AudioResponse
import rx.music.ui.audio.AudioAdapter
import rx.music.ui.audio.AudioPresenter
import rx.music.ui.audio.AudioView
import rx.music.ui.audio.InfiniteScrollListener
import rx.music.ui.auth.AuthController
import rx.music.ui.main.MainActivity

/** Created by Maksim Sukhotski on 5/6/2017. */
class PickerController : MoxyController(), AudioView {
    @InjectPresenter lateinit var audioPresenter: AudioPresenter

    private var adapter: AudioAdapter? = null

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_picker, container, false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
    }

    override fun onViewBound(view: View) = with(view) {
        descriptionTextView.movementMethod = LinkMovementMethod.getInstance()
        dialogContainer.onClick {
            handleBack()
            router.popCurrentController()
        }
        adapter = AudioAdapter(onClick = { audio, position ->
            run {
                audioPresenter.handleAudio(audio)
                audioPresenter.savePosition(position)
            }
        })
        val layoutManager = LinearLayoutManager(activity)
        pickerRecycler.adapter = adapter
        pickerRecycler.setHasFixedSize(true)
        pickerRecycler.layoutManager = layoutManager
        pickerRecycler.addOnScrollListener(InfiniteScrollListener({
            audioPresenter.getAudio(offset = adapter?.itemCount!!)
        }, layoutManager))
    }

    override fun showAudio(audioResponse: AudioResponse): Unit = adapter!!.addAndNotify(audioResponse.items)

    override fun showAuthController() = router.setRoot(RouterTransaction.with(AuthController())
            .pushChangeHandler(HorizontalChangeHandler())
            .popChangeHandler(HorizontalChangeHandler()))

    override fun handleBack(): Boolean {
        if (!(activity as MainActivity).isAnimate) {
            (activity as MainActivity).isAnimate = true
            view?.postDelayed({
                (activity as MainActivity).roomContainer.elevation = 0f
                (activity as MainActivity).slidingLayout.setParallaxOffset(resources!!.getDimension(R.dimen.navigation).toInt())
                (activity as MainActivity).resetAnimationMode()
                (activity as MainActivity).resetSlidingPanel()
            }, 400)
            return super.handleBack()
        }
        return false
    }

    override fun showPlayer(audio: Audio) = (activity as MainActivity).mainPresenter.updatePlayer(audio)

    override fun showSelectedPos(position: Int) = adapter!!.selectAndNotify(position)
}