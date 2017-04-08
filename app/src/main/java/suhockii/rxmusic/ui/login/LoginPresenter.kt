package suhockii.rxmusic.ui.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.HttpException
import suhockii.rxmusic.App
import suhockii.rxmusic.business.auth.AuthInteractorImpl
import suhockii.rxmusic.data.repositories.auth.models.Captcha
import suhockii.rxmusic.data.repositories.auth.models.Validation
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/1/2017.*/
@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

    @Inject lateinit var preferencesRepository: PreferencesRepository

    init {
        App.appComponent.inject(this)
    }


    private val authInteractor = AuthInteractorImpl()

    private fun validatePhone(validation: Validation?) {
        authInteractor.validatePhone(validation!!.validation_sid).subscribe()
        viewState.showValidate(validation)
    }

    fun login(username: String, password: String, captchaSid: String? = null, captchaKey: String? = null, code: String? = null) {
        if (username.isNotEmpty() && password.isNotEmpty())
            authInteractor.login(username, password, captchaSid, captchaKey, code)
                    .subscribe(
                            {
                                preferencesRepository.accessToken = it.access_token
                                preferencesRepository.secret = it.secret
                            },
                            {
                                if (it is HttpException) {
                                    val s = it.response().errorBody().string()
                                    when (JSONObject(s).get("error").toString()) {
                                        "invalid_client" -> viewState.showLogin(JSONObject(s).get("error_description").toString())
                                        "need_validation" -> validatePhone(Gson().fromJson(s, Validation::class.java))
                                        "need_captcha" -> viewState.showCaptcha(Gson().fromJson(s, Captcha::class.java))
                                    }
                                }
                            }
                    )
    }
}