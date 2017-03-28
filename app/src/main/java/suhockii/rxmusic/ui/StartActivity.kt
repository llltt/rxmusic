package suhockii.rxmusic.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import suhockii.rxmusic.R
import suhockii.rxmusic.business.vk.auth.AuthInteractorImpl

class StartActivity : AppCompatActivity() {

    private val authInteractor = AuthInteractorImpl()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val usernameEditText = findViewById(R.id.usernameEditText) as EditText
        val passwordEditText = findViewById(R.id.passwordEditText) as EditText
        val loginButton = findViewById(R.id.loginButton) as Button
        loginButton.setOnClickListener {
            authInteractor
                    .login(usernameEditText.text.toString(), passwordEditText.text.toString())
                    .subscribe(
                            {
                                it
                            }, {
                        it.printStackTrace()
                    }
                    )
        }

    }
}
