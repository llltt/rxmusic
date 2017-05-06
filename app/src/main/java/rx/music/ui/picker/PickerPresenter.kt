package rx.music.ui.picker

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import rx.music.App
import rx.music.business.audio.AudioInteractor
import javax.inject.Inject

/** Created by Maksim Sukhotski on 5/6/2017. */
@InjectViewState
class PickerPresenter : MvpPresenter<PickerView>() {

    @Inject lateinit var audioInteractor: AudioInteractor

    init {
        App.instance.userComponent?.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        getAudio()
    }

    private fun getAudio(ownerId: Long? = null, count: Int = 30, offset: Int = 0) =
            audioInteractor.getAudio(ownerId, count, offset)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ it ->
                        run {
                            if (it.response != null) viewState.showAudio(it.response)
                            else if (it.error.error_code == 5) viewState.showAuthController()
                        }
                    })


}