package rx.music.ui.player

import android.media.MediaPlayer
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.support.RouterPagerAdapter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.realm.Realm
import io.realm.RealmList
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.models.vk.Audio
import rx.music.net.models.vk.Audios
import java.util.*
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/2/2017. */
@InjectViewState
class PlayerPresenter(val realm: Realm, val playerController: PlayerController) : MvpPresenter<PlayerView>() {
    @Inject lateinit var mediaPlayer: MediaPlayer
    @Inject lateinit var preferencesRepo: PreferencesRepo
    @Inject lateinit var audioInteractor: AudioInteractor

    var timer: Timer? = null

    override fun onFirstViewAttach() {
        Dagger.instance.userComponent?.inject(this)
        super.onFirstViewAttach()
        viewState.showAlpha(null)
        setPager()
    }

    fun updatePlayer(audio: Audio) {
        viewState.showPlayer(audio)
        timer?.cancel()
        timer = kotlin.concurrent.timer(initialDelay = 0, period = 100) { viewState.showSeekBar(mediaPlayer) }
    }

    fun makeInvisible(i: Int?) {
        viewState.showAlpha(i)
    }

    fun setPager(): Disposable = with(preferencesRepo.credentials) {
        Single
                .fromCallable {
                    val audios = realm.where(Audios::class.java)
                            .equalTo(Audios::userId.name, userId)
                            .findFirst()?.items
//                    return@fromCallable audios
                    if (audios != null) return@fromCallable audios
                    else return@fromCallable RealmList<Audio>()
                }
                .filter({ it?.isValid ?: false && it?.isManaged ?: false })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showPager(object : RouterPagerAdapter(playerController) {
                        override fun getCount(): Int = Int.MAX_VALUE

                        override fun configureRouter(router: Router, position: Int) {
                            if (!router.hasRootController() && it != null) {
                                val page = PictureController(it[position].albumPhoto)
                                router.setRoot(RouterTransaction.with(page))
                            }
                        }

                        override fun getPageTitle(position: Int): CharSequence {
                            return "Page " + position
                        }
                    })
                })
    }
}