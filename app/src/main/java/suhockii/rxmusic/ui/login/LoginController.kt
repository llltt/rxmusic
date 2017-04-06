package suhockii.rxmusic.ui.login

import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bumptech.glide.Glide
import suhockii.rxmusic.R
import suhockii.rxmusic.data.repositories.auth.models.Captcha
import suhockii.rxmusic.data.repositories.auth.models.Validation
import suhockii.rxmusic.extension.FlipLayout
import suhockii.rxmusic.extension.findView
import suhockii.rxmusic.extension.onClick
import suhockii.rxmusic.ui.base.MoxyController

class LoginController : MoxyController(), LoginView {

    override fun inflateView(inflater: LayoutInflater, container: ViewGroup): View {
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun getTitle(): String = "rxmusic"

    override fun onAttach(view: View) {
        super.onAttach(view)
        (view.findViewById(R.id.loginButton) as Button).onClick {
            presenter.login((view.findViewById(R.id.usernameEditText) as EditText).text.toString(),
                    (view.findViewById(R.id.passwordEditText) as EditText).text.toString())
        }
    }

    @InjectPresenter
    lateinit var presenter: LoginPresenter

    override fun showSnackbar(text: String) {
        Snackbar.make(activity!!.findViewById(R.id.startLayout), text, Snackbar.LENGTH_LONG)
                .show()
    }

    override fun showCaptcha(captcha: Captcha) {
        val flipLayout: FlipLayout = findView(R.id.flipLayout)
        if (flipLayout.view0?.visibility == View.VISIBLE) flipLayout.toggleDown()
        Glide.with(activity!!)
                .load(captcha.captcha_img)
                .error(R.drawable.oh)
                .into(findView(R.id.captchaImageView))
        findView<Button>(R.id.loginButton).onClick {
            presenter.login(findView<EditText>(R.id.usernameEditText).text.toString(),
                    findView<EditText>(R.id.passwordEditText).text.toString(),
                    captcha.captcha_sid,
                    findView<EditText>(R.id.captchaEditText).text.toString())
        }
    }

    override fun showLogin(toString: String) {
        val flipLayout: FlipLayout = findView(R.id.flipLayout)
        flipLayout.thirdViewEnabled = false
        if (flipLayout.view0?.visibility != View.VISIBLE) flipLayout.toggleDown()
        showSnackbar(toString)
    }


    override fun showValidate(validation: Validation) {
        val flipLayout = activity!!.findViewById(R.id.flipLayout) as FlipLayout
        flipLayout.thirdViewEnabled = true
        (activity!!.findViewById(R.id.validateTextView) as TextView).text = "Код отправлен на номер ${validation.phone_mask}"
        if (flipLayout.view2?.visibility != View.VISIBLE) flipLayout.toggleUp()
        findView<Button>(R.id.loginButton).onClick {
            presenter.login(findView<EditText>(R.id.usernameEditText).text.toString(),
                    findView<EditText>(R.id.passwordEditText).text.toString(),
                    code = findView<EditText>(R.id.validateEditText).text.toString())
        }
    }
}
