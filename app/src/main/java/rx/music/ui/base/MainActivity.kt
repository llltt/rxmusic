package rx.music.ui.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import kotlinx.android.synthetic.main.activity_main.*
import rx.music.R
import rx.music.ui.audio.AudioController

/** Created by Maksim Sukhotski on 4/6/2017. */
class MainActivity : AppCompatActivity() {

    private var router: Router? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        router = Conductor.attachRouter(this, controllerContainer, savedInstanceState)
        if (!router!!.hasRootController()) {
            router!!.setRoot(RouterTransaction.with(AudioController()))
        }
    }

    override fun onBackPressed() {
        if (!router!!.handleBack()) {
            super.onBackPressed()
        }
    }

}