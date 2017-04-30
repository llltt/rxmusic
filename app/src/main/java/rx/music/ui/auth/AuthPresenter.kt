package rx.music.ui.auth

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.HttpException
import rx.music.App
import rx.music.business.auth.AuthInteractor
import rx.music.business.preferences.PreferencesInteractor
import rx.music.data.net.models.Captcha
import rx.music.data.net.models.Credentials
import rx.music.data.net.models.Validation
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/1/2017.*/
@InjectViewState
class AuthPresenter : MvpPresenter<AuthView>() {

    @Inject lateinit var preferencesInteractor: PreferencesInteractor
    @Inject lateinit var authInteractor: AuthInteractor

    init {
        App.instance.authComponent?.inject(this)
    }

    fun login(username: String,
              password: String,
              captchaSid: String? = null,
              captchaKey: String? = null,
              code: String? = null) {
        if (username.isNotEmpty() && password.isNotEmpty())
            authInteractor.getCredentials(username, password, captchaSid, captchaKey, code)
                    .subscribe(onResponse(), onError())
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

    private fun onResponse(): (Credentials) -> Unit {
        return {
            preferencesInteractor.saveCredentials(it)
            App.instance.authComponent = null
            viewState.showNextController()
        }
    }

    private fun validatePhone(validation: Validation?) {
        authInteractor.validatePhone(validation!!.validation_sid).subscribe()
        viewState.showValidation(validation)
    }
}