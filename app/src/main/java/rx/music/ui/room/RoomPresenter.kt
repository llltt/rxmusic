package rx.music.ui.room

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.music.business.audio.AudioInteractor
import rx.music.business.users.UsersInteractor
import rx.music.dagger.Dagger
import rx.music.ui.popular.RoomView
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/1/2017. */
@InjectViewState
class RoomPresenter : MvpPresenter<RoomView>() {

    @Inject lateinit var audioInteractor: AudioInteractor
    @Inject lateinit var usersInteractor: UsersInteractor

    override fun onFirstViewAttach() {
        Dagger.instance.userComponent?.inject(this)
        super.onFirstViewAttach()
        usersInteractor.getAuthorized()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()
    }

//    fun getUser() {
//        usersInteractor.getAuthorized()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe({ it. })
//    }
}