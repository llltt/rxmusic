package suhockii.rxmusic.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import kotlinx.android.synthetic.main.activity_start.*
import suhockii.rxmusic.R
import suhockii.rxmusic.extension.onClick

class StartActivity : MvpAppCompatActivity(), StartView {

    @InjectPresenter
    lateinit var presenter: StartPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        loginButton.onClick {
            presenter.login(usernameEditText.text.toString(), passwordEditText.text.toString())
        }
    }

    override fun showSnackbar(text: String) {
        Snackbar.make(startLayout, text, Snackbar.LENGTH_LONG)
                .setAction("Очистить", { presenter.clean() })
                .show()
    }

    override fun clean() {
        passwordEditText.text.clear()
    }
}
