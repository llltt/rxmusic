package suhockii.rxmusic.ui.auth

import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.controller_login.view.*
import kotlinx.android.synthetic.main.part_captcha.view.*
import kotlinx.android.synthetic.main.part_login.view.*
import kotlinx.android.synthetic.main.part_validation.view.*
import suhockii.rxmusic.App
import suhockii.rxmusic.R
import suhockii.rxmusic.data.repositories.auth.models.Captcha
import suhockii.rxmusic.data.repositories.auth.models.Validation
import suhockii.rxmusic.smth.hideKeyboard
import suhockii.rxmusic.smth.onClick
import suhockii.rxmusic.ui.audio.AudioController
import suhockii.rxmusic.ui.base.MoxyController


class AuthController : MoxyController(), AuthView {

    @InjectPresenter
    lateinit var presenter: AuthPresenter

    override fun getTitle(): String = "rxmusic"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_login, container, false)
    }

    init {
        App.instance.initAuthComponent()
    }

    override fun setupControllerComponent() {
        App.authComponent.inject(this)
    }

    override fun onViewBound(view: View) {
        view.loginButton.onClick {
            presenter.login(view.usernameEditText.text.toString(), view.passwordEditText.text.toString())
        }
    }

    override fun showSnackbar(text: String) {
        with(view!!) {
            Snackbar.make(loginLayout, text, Snackbar.LENGTH_LONG)
                    .show()
        }
    }

    override fun showLogin(toString: String) {
        with(view!!) {
            flipLayout.showView(flipLayout.loginView!!)
            showSnackbar(toString)
        }
    }

    override fun showCaptcha(captcha: Captcha) {
        with(view!!) {
            flipLayout.showView(flipLayout.captchaView!!)
            Glide.with(activity!!)
                    .load(captcha.captcha_img)
                    .error(R.drawable.oh)
                    .into(captchaImageView)
            loginButton.onClick {
                presenter.login(usernameEditText.text.toString(),
                        passwordEditText.text.toString(),
                        captcha.captcha_sid,
                        captchaEditText.text.toString())
                captchaEditText.text.clear()
            }
        }
    }

    override fun showValidation(validation: Validation) {
        with(view!!) {
            flipLayout.showView(flipLayout.validationView!!)
            validationTextView.text = context.getString(R.string.code_sent, validation.phone_mask)
            loginButton.onClick {
                presenter.login(usernameEditText.text.toString(),
                        passwordEditText.text.toString(),
                        code = validationEditText.text.toString())
            }
        }
    }

    override fun showNextController() {
        view?.hideKeyboard()
        router.setRoot(RouterTransaction.with(AudioController())
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
    }
}
