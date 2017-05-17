package rx.music.ui.audio

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import me.extensions.isNotNull
import rx.music.business.audio.AudioInteractor
import rx.music.dagger.Dagger
import rx.music.data.preferences.PreferencesRepo
import rx.music.data.realm.models.Audio
import rx.music.data.realm.models.User
import rx.music.net.models.Response
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/8/2017. */
@InjectViewState
class AudioPresenter(val realm: Realm) : MvpPresenter<AudioView>() {
    @Inject lateinit var audioInteractor: AudioInteractor
    @Inject lateinit var preferencesRepo: PreferencesRepo

    init {
        Dagger.instance.userComponent?.inject(this@AudioPresenter)
    }

    override fun onFirstViewAttach() = with(preferencesRepo.credentials) {
        super.onFirstViewAttach()
        getAudio()
    }

    fun initAdapter(users: Response<List<User>>) = with(preferencesRepo.credentials) {
        viewState.showRecycler(AudioAdapter(realm.where(User::class.java)
                .equalTo(User::id.name, users.response?.get(0)?.id ?: user_id)
                .findFirst()?.audioList,
                onClick = { audio, pos -> handleAudio(realm.copyFromRealm(audio), pos) }))
    }

    fun getAudio(ownerId: Long? = null, count: Int = 30, offset: Int = 0) {
        audioInteractor.getAudio(ownerId, count, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

    fun handleAudio(audio: Audio, pos: Int): Disposable = with(viewState) {
        audioInteractor.handleAudio(audio)
                .filter { audio.isNotNull }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { showPlayer(audio); showSelectedPos(pos) }
                .subscribe({ audio -> showPlayer(audio) })
    }
}
