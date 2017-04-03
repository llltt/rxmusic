package suhockii.rxmusic.ui

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import org.json.JSONObject
import retrofit2.HttpException
import suhockii.rxmusic.business.vk.auth.AuthInteractorImpl
import suhockii.rxmusic.data.repositories.auth.models.Auth

/** Created by Maksim Sukhotski on 4/1/2017.*/
@InjectViewState
class StartPresenter : MvpPresenter<StartView>() {

    private val authInteractor = AuthInteractorImpl()

    fun login(username: String, password: String) {
        authInteractor.login(username, password)
                .subscribe(onSuccess(), onException())
    }

    fun clean() {
        viewState.clean()
    }

    private fun onSuccess(): ((t: Auth) -> Unit)? {
        return { viewState.showSnackbar(it.access_token) }
    }

    private fun onException(): (Throwable) -> Unit {
        return {
            if (it is HttpException) {
                val string = it.response().errorBody().string()
                val text = JSONObject(string).get("error_description").toString()
                viewState.showSnackbar(text)
            }
        }
    }
}