package rx.music.ui.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.Gson
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import rx.music.business.auth.AuthInteractor
import rx.music.dagger.Dagger
import rx.music.net.models.Captcha
import rx.music.net.models.Validation
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/1/2017.*/
@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    @Inject lateinit var authInteractor: AuthInteractor

    init {
        Dagger.instance.authComponent?.inject(this)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.hideNavigation()
    }

    fun login(username: String, password: String, captchaSid: String? = null,
              captchaKey: String? = null, code: Int? = null) {
        if (username.isNotEmpty() && password.isNotEmpty())
            authInteractor.getCredentials(username, password, captchaSid, captchaKey, code)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ viewState.showAudioController() }, onError())
    }

    private fun onError(): (Throwable) -> Unit {
        return {
            if (it is HttpException) {
                val s = it.response().errorBody().string()
                when (JSONObject(s).get("error").toString()) {
                    "invalid_client" -> viewState.showLogin(JSONObject(s).get("error_description"))
                    "need_validation" -> validatePhone(Gson().fromJson(s, Validation::class.java))
                    "need_captcha" -> viewState.showCaptcha(Gson().fromJson(s, Captcha::class.java))
                }
            }
        }
    }

    private fun validatePhone(validation: Validation?) {
        authInteractor.validatePhone(validation!!.validation_sid).subscribe()
        viewState.showValidation(validation)
    }
}