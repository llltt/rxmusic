package rx.music.ui.base

import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bumptech.glide.Glide
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener
import kotlinx.android.synthetic.main.part_containers.*
import kotlinx.android.synthetic.main.part_player_preview.*
import rx.music.R
import rx.music.data.network.models.Audio
import rx.music.ui.audio.AudioController
import rx.music.ui.popular.PopularController
import rx.music.ui.popular.RoomController


/** Created by Maksim Sukhotski on 4/6/2017. */
class MainActivity : MvpAppCompatActivity(), MainView, BottomSheetListener {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    private var audioRouter: Router? = null
    private var popularRouter: Router? = null
    private var roomRouter: Router? = null

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        moreImageView.setOnClickListener { _ -> showMoreMenu() }
        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener)
        audioRouter = Conductor.attachRouter(this, audioContainer, savedInstanceState)
        popularRouter = Conductor.attachRouter(this, popularContainer, savedInstanceState)
        roomRouter = Conductor.attachRouter(this, roomContainer, savedInstanceState)
        if (!audioRouter!!.hasRootController())
            audioRouter!!.setRoot(RouterTransaction.with(AudioController()).tag("audio"))
        if (!popularRouter!!.hasRootController())
            popularRouter!!.setRoot(RouterTransaction.with(PopularController()))
        if (!roomRouter!!.hasRootController())
            roomRouter!!.setRoot(RouterTransaction.with(RoomController()))
    }

    private fun showMoreMenu() {
        BottomSheet.Builder(this)
                .setSheet(R.menu.more)
                .setListener(this)
                .show()
    }

    override fun showPlayer(audio: Audio) {
        Glide.with(this)
                .load(audio.pic)
                .error(R.drawable.audio_row_placeholder_2x)
                .into(playerPreviewImageView)
    }

    override fun onSheetDismissed(p0: BottomSheet, p1: Int) {}
    override fun onSheetShown(p0: BottomSheet) {}
    override fun onSheetItemSelected(p0: BottomSheet, p1: MenuItem?) {
        if (p1?.itemId == R.id.share) mainPresenter.logout()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        val visibleRouter = getVisibleRouter()
        if (!visibleRouter?.handleBack()!!) {
            if (visibleRouter !== audioRouter) {
                bottomNavigation.selectedItemId = R.id.music
            } else {
                super.onBackPressed()
            }
        }
    }

    private fun getVisibleRouter(): Router? {
        if (popularContainer.visibility == View.VISIBLE)
            return popularRouter
        if (roomContainer.visibility == View.VISIBLE)
            return roomRouter
        return audioRouter
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
        audioContainer.visibility = if (container == null || container.id == R.id.audioContainer)
            View.VISIBLE else View.GONE
        popularContainer.visibility = if (container?.id == R.id.popularContainer)
            View.VISIBLE else View.GONE
        roomContainer.visibility = if (container?.id == R.id.roomContainer)
            View.VISIBLE else View.GONE
        if (!isReselected) {
            container?.alpha = 0F
            container?.animate()?.alpha(1F)
        }
    }

    override fun showAuthController() {
        (audioRouter?.getControllerWithTag("audio") as AudioController).audioPresenter
                .viewState.showAuthController()
    }
}