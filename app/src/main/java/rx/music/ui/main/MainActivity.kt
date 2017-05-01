package rx.music.ui.main

import android.support.design.widget.BottomNavigationView
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import kotlinx.android.synthetic.main.part_containers.*
import rx.music.R
import rx.music.ui.audio.AudioController
import rx.music.ui.popular.PopularController
import rx.music.ui.popular.RoomController

/** Created by Maksim Sukhotski on 4/6/2017. */
class MainActivity : MvpAppCompatActivity(), MainView {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    private var audioRouter: Router? = null
    private var popularRouter: Router? = null
    private var roomRouter: Router? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener)
        audioRouter = Conductor.attachRouter(this, audioContainer, savedInstanceState)
        popularRouter = Conductor.attachRouter(this, popularContainer, savedInstanceState)
        roomRouter = Conductor.attachRouter(this, roomContainer, savedInstanceState)
        if (!audioRouter!!.hasRootController())
            audioRouter!!.setRoot(RouterTransaction.with(AudioController()))
        if (!popularRouter!!.hasRootController())
            popularRouter!!.setRoot(RouterTransaction.with(PopularController()))
        if (!roomRouter!!.hasRootController())
            roomRouter!!.setRoot(RouterTransaction.with(RoomController()))

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        if (!audioRouter!!.handleBack()) {
            super.onBackPressed()
        }
    }

    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val isReselected = bottomNavigation.selectedItemId == item.itemId
        when (item.itemId) {
            R.id.music -> mainPresenter.showContainer(audioContainer, isReselected)
            R.id.popular -> mainPresenter.showContainer(popularContainer, isReselected)
            R.id.room -> mainPresenter.showContainer(roomContainer, isReselected)
        }
        true
    }

    override fun showContainer(container: ChangeHandlerFrameLayout?, isReselected: Boolean) {
        audioContainer.visibility = if (container == null || container == audioContainer)
            View.VISIBLE else View.GONE
        popularContainer.visibility = if (container == popularContainer) View.VISIBLE else View.GONE
        roomContainer.visibility = if (container == roomContainer) View.VISIBLE else View.GONE
        if (isReselected) {
            container?.alpha = 0F
            container?.animate()?.alpha(1F)
        }
    }

}