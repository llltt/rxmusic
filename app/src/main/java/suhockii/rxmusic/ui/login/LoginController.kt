package suhockii.rxmusic.ui.login

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
import kotlinx.android.synthetic.main.part_validate.view.*
import suhockii.rxmusic.R
import suhockii.rxmusic.data.repositories.auth.models.Captcha
import suhockii.rxmusic.data.repositories.auth.models.Validation
import suhockii.rxmusic.extension.hideKeyboard
import suhockii.rxmusic.extension.onClick
import suhockii.rxmusic.ui.audio.AudioController
import suhockii.rxmusic.ui.base.MoxyController


class LoginController : MoxyController(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun getTitle(): String = "rxmusic"

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.controller_login, container, false)
    }

    override fun onAttach(view: View) {
        super.onAttach(view)
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
            flipLayout.showView(flipLayout.view0!!)
            showSnackbar(toString)
        }
    }

    override fun showCaptcha(captcha: Captcha) {
        with(view!!) {
            flipLayout.showView(flipLayout.view1!!)
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

    override fun showValidate(validation: Validation) {
        with(view!!) {
            flipLayout.showView(flipLayout.view2!!)
            validateTextView.text = context.getString(R.string.code_sent, validation.phone_mask)
            loginButton.onClick {
                presenter.login(usernameEditText.text.toString(),
                        passwordEditText.text.toString(),
                        code = validateEditText.text.toString())
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
