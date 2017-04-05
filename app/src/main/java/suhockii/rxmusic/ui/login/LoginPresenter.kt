package suhockii.rxmusic.ui.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.google.gson.Gson
import org.json.JSONObject
import retrofit2.HttpException
import suhockii.rxmusic.business.vk.auth.AuthInteractorImpl
import suhockii.rxmusic.data.repositories.auth.models.Auth
import suhockii.rxmusic.data.repositories.auth.models.Captcha
import suhockii.rxmusic.data.repositories.auth.models.Validation

/** Created by Maksim Sukhotski on 4/1/2017.*/
@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {

    private val authInteractor = AuthInteractorImpl()

    private fun validatePhone(validation: Validation?) {
        authInteractor.validatePhone(validation!!.validation_sid).subscribe()
        viewState.showValidate(validation)
    }

    fun login(username: String, password: String, captchaSid: String? = null, captchaKey: String? = null, code: String? = null) {
        if (username.isNotEmpty() && password.isNotEmpty()) {
            authInteractor.login(username, password, captchaSid, captchaKey, code)
                    .subscribe(onSuccess(), onException())
        }
    }

    private fun onSuccess(): ((t: Auth) -> Unit)? {
        return { viewState.showSnackbar(it.access_token) }
    }

    private fun onException(): (Throwable) -> Unit {
        return {
            if (it is HttpException) {
                val string = it.response().errorBody().string()
                when (JSONObject(string).get("error").toString()) {
                    "invalid_client" -> viewState.showLogin(JSONObject(string).get("error_description").toString())
                    "need_validation" -> validatePhone(Gson().fromJson(string, Validation::class.java))
                    "need_captcha" -> viewState.showCaptcha(Gson().fromJson(string, Captcha::class.java))
                }
            }
        }
    }
}