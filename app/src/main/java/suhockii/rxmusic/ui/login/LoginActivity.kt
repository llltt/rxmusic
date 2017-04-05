package suhockii.rxmusic.ui.login

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.part_captcha.*
import kotlinx.android.synthetic.main.part_login.*
import kotlinx.android.synthetic.main.part_validate.*
import suhockii.rxmusic.R
import suhockii.rxmusic.data.repositories.auth.models.Captcha
import suhockii.rxmusic.data.repositories.auth.models.Validation
import suhockii.rxmusic.extension.onClick

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginButton.onClick {
            presenter.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    override fun showSnackbar(text: String) {
        Snackbar.make(startLayout, text, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showCaptcha(captcha: Captcha) {
        if (flipLayout.view0?.visibility == View.VISIBLE) flipLayout.toggleDown()
        Glide.with(this)
                .load(captcha.captcha_img)
                .error(R.drawable.oh)
                .into(captchaImageView)
        loginButton.onClick {
            presenter.login(usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                    captcha.captcha_sid,
                    captchaEditText.text.toString())
        }
    }

    override fun showLogin(toString: String) {
        flipLayout.thirdViewEnabled = false
        if (flipLayout.view0?.visibility != View.VISIBLE) flipLayout.toggleDown()
        showSnackbar(toString)
    }

    override fun showValidate(validation: Validation) {
        flipLayout.thirdViewEnabled = true
        validateTextView.text = "Код отправлен на номер ${validation.phone_mask}"
        if (flipLayout.view2?.visibility != View.VISIBLE) flipLayout.toggleUp()
        loginButton.onClick {
            presenter.login(usernameEditText.text.toString(),
                    passwordEditText.text.toString(),
                    code = validateEditText.text.toString())
        }
    }
}
