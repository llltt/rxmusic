package rx.music.ui.audio

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import me.extensions.isNotNull
import me.extensions.toStr
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.net.models.base.Response
import rx.music.net.models.vk.Audio
import rx.music.net.models.vk.MusicPage
import rx.music.net.models.vk.User
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/8/2017. */
@InjectViewState
class AudioPresenter(val realm: Realm) : MvpPresenter<AudioView>() {
    @Inject lateinit var audioInteractor: AudioInteractor
    @Inject lateinit var preferencesRepo: PreferencesRepo

    init {
        Dagger.instance.userComponent?.inject(this@AudioPresenter)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getMusicPage()
    }

    fun getMusicPage(audioCount: Int? = null, audioOffset: Int? = null): Disposable =
            with(preferencesRepo.credentials) {
                audioInteractor.getMusicPage(audioCount = audioCount, audioOffset = audioOffset)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ audio, error -> handleMusicPage(audio, error) })
            }

    private fun handleMusicPage(audio: Response<MusicPage>?, error: Throwable?) {
        if (audio != null) {
            if (audio.executeErrors != null) viewState.showSnackbar(audio.executeErrors.toStr())
        }
        if (error != null) viewState.showSnackbar(error.localizedMessage)
    }

    fun onUserReceived(users: Response<List<User>>) =
            with(preferencesRepo.credentials) {
                val audioList = realm.where(User::class.java)
                        .equalTo(User::id.name, users.response?.get(0)?.id ?: userId)
                        .findFirst()?.audioList
                if (audioList?.isEmpty() ?: true) getAudio()
                viewState.showRecycler(AudioAdapter(audioList,
                        onClick = { audio, pos -> handleAudio(realm.copyFromRealm(audio), pos) }))
            }

    fun getAudio(ownerId: Long? = null, count: Int = 15, offset: Int = 0) {
        audioInteractor.getAudio(ownerId, count, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter { it.error.isNotNull }
                .subscribe({ viewState.showSnackbar(it.error!!.error_msg) })
    }

    fun handleAudio(audio: Audio, pos: Int): Disposable =
            with(viewState) {
                audioInteractor.handleAudio(audio)
                        .filter { audio.isNotNull }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { showPlayer(audio); showSelectedPos(pos) }
                        .subscribe({ audio -> showPlayer(audio) })
            }
}

