package suhockii.rxmusic.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import kotlinx.android.synthetic.main.activity_main.*
import suhockii.rxmusic.App
import suhockii.rxmusic.R
import suhockii.rxmusic.ui.audio.AudioController
import suhockii.rxmusic.ui.base.ActionBarProvider

/** Created by Maksim Sukhotski on 4/6/2017. */
class MainActivity : AppCompatActivity(), ActionBarProvider {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState)
        if (!router!!.hasRootController()) {
            App.instance.initUserComponent()
            router!!.setRoot(RouterTransaction.with(AudioController()))
        }
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }

}