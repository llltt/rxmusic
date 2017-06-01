package rx.music.ui.player

import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.controller_player.view.*
import kotlinx.android.synthetic.main.part_player_preview.view.*
import me.base.MoxyController
import rx.music.R
import rx.music.net.models.vk.Audio

/** Created by Maksim Sukhotski on 5/2/2017. */
class PlayerController : MoxyController(), PlayerView {

    @InjectPresenter
    lateinit var playerPresenter: PlayerPresenter

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_player, container, false)
    }

    override fun onViewBound(view: View) {
        with(view) {
        }
    }

    override fun showPlayer(audio: Audio): Unit = with(view!!) {
        playerArtistTextView.text = audio.artist
        playerTitleTextView.text = audio.title
        artistTextView.text = audio.artist
        titleTextView.text = audio.title
        Glide.with(activity)
                .load(if (audio.album.thumb.photo600!!.isNotBlank())
                    audio.album.thumb.getSuitablePhoto() else audio.googlePhoto.photo)
                .error(R.drawable.audio_row_placeholder_2x)
                .centerCrop()
                .into(playerPreviewImageView)
        Glide.with(activity)
                .load(if (audio.album.thumb.photo600!!.isNotBlank())
                    audio.album.thumb.getSuitablePhoto() else audio.googlePhoto.photo)
                .error(R.drawable.audio_row_placeholder_2x)
                .centerCrop()
                .into(playerImageView)
    }

    override fun showSeekBar(mp: MediaPlayer) = with(view!!) {
        seekBar.max = mp.duration
        seekBar.progress = mp.currentPosition
    }

    override fun onPanelSlide(slideOffset: Float) = with(view!!) {
        if (playerPreviewInclude.visibility == View.VISIBLE) playerPreviewInclude?.alpha = 1 - slideOffset * 2
        else roomPreviewInclude?.alpha = 1 - slideOffset * 2
        val fl = slideOffset * 2 - 1
        playerButtonsInclude?.alpha = fl
        seekBar?.alpha = fl
        previousImageView?.alpha = fl
        nextImageView?.alpha = fl
        nextImageView?.alpha = fl
        playImageView?.alpha = fl
    }

    override fun onPanelStateChanged(newState: SlidingUpPanelLayout.PanelState?, isRoom: Boolean) = with(view!!) {
        when (newState?.ordinal) {
            0 -> playerPresenter.makeAlpha(if (isRoom) roomPreviewInclude else playerPreviewInclude)
            1 -> playerPresenter.makeAlpha(playerButtonsInclude)
        }
    }

    override fun showAlpha(view: View?) {
        if (view == null || view.id == R.id.playerButtonsInclude) {
            view?.playerButtonsInclude?.alpha = 0f
            view?.playerPreviewInclude?.alpha = 1f
            view?.roomPreviewInclude?.alpha = 1f
        } else {
            view.playerPreviewInclude?.alpha = 0f
            view.roomPreviewInclude?.alpha = 0f
            view.playerButtonsInclude?.alpha = 1f
        }
    }
}