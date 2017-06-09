package rx.music.ui.main

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.view.MenuItem
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.bluelinelabs.conductor.ChangeHandlerFrameLayout
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.bluelinelabs.conductor.changehandler.HorizontalChangeHandler
import com.kennyc.bottomsheet.BottomSheet
import com.kennyc.bottomsheet.BottomSheetListener
import com.sothree.slidinguppanel.SlidingUpPanelLayout
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.controller_player.*
import kotlinx.android.synthetic.main.part_main_containers.*
import kotlinx.android.synthetic.main.part_player_container.*
import kotlinx.android.synthetic.main.part_search_preview.*
import me.extensions.*
import rx.music.R
import rx.music.net.BaseFields
import rx.music.ui.audio.AudioController
import rx.music.ui.auth.AuthController
import rx.music.ui.player.PlayerController
import rx.music.ui.popular.PopularController
import rx.music.ui.popular.RoomController


/** Created by Maksim Sukhotski on 4/6/2017. */

class MainActivity : MvpAppCompatActivity(), MainView, BottomSheetListener,
        SlidingUpPanelLayout.PanelSlideListener {

    @InjectPresenter
    lateinit var mainPresenter: MainPresenter

    private var audioRouter: Router? = null
    private var popularRouter: Router? = null
    private var roomRouter: Router? = null
    var playerRouter: Router? = null
    private var isRoom: Boolean = false
    private var isSearch: Boolean = false
    val delay: Long = 150
    private val keyboardDelay = 75L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        BaseFields.musicHeight = (50)
                .toPx(resources)
                .closestFrom(mutableListOf(34, 68, 135, 270, 300, 600))
        artistTextView; titleTextView
        slidingLayout.addPanelSlideListener(this)
        bottomNavigation.setOnNavigationItemSelectedListener(navigationListener)
        audioRouter = Conductor.attachRouter(this, audioContainer, savedInstanceState)
        popularRouter = Conductor.attachRouter(this, popularContainer, savedInstanceState)
        roomRouter = Conductor.attachRouter(this, roomContainer, savedInstanceState)
        playerRouter = Conductor.attachRouter(this, playerContainer, savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        searchEditText.setKeyboardVisibilityListener({}, {
            if (isSearch) {
                isSearch = false
                playerPreviewInclude.visibility = VISIBLE
                searchPreviewInclude.animate()
                        .translationY(-playerPreviewInclude.height.toFloat())
                        .startDelay = keyboardDelay
                playerPreviewInclude.animate()
                        .alpha(1f)
                        .startDelay = keyboardDelay
                playerPreviewInclude.animate()
                        .translationY(0f)
                        .withEndAction({ searchPreviewInclude.visibility = INVISIBLE })
                        .startDelay = keyboardDelay
            }
            bottomNavigation.visibility = VISIBLE
        })
    }

    override fun showOnAuthorized(isAfterAuth: Boolean) {
        if (isAfterAuth) audioRouter!!.setRoot(RouterTransaction.with(AudioController())
                .tag("audio")
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
        if (!audioRouter!!.hasRootController()) {
        }
        audioRouter!!.setRoot(RouterTransaction.with(AudioController()).tag("audio"))
        if (!popularRouter!!.hasRootController())
            popularRouter!!.setRoot(RouterTransaction.with(PopularController()))
        if (!roomRouter!!.hasRootController())
            roomRouter!!.setRoot(RouterTransaction.with(RoomController()))
        if (!playerRouter!!.hasRootController())
            playerRouter!!.setRoot(RouterTransaction
                    .with(PlayerController())
                    .tag(PlayerController::javaClass.name))
    }

    override fun showPlayListScreen() {
        slidingLayout.panelState = SlidingUpPanelLayout.PanelState.HIDDEN
    }

    override fun onSheetDismissed(p0: BottomSheet, p1: Int) {}
    override fun onSheetShown(p0: BottomSheet) {}
    override fun onSheetItemSelected(p0: BottomSheet, p1: MenuItem?) {
        if (p1?.itemId == R.id.share) mainPresenter.logout()
    }

    override fun onBackPressed() {
        if (slidingLayout.panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            slidingLayout.panelState = SlidingUpPanelLayout.PanelState.COLLAPSED
            return
        }
        val visibleRouter = getVisibleRouter()
        if (!visibleRouter?.handleBack()!!) {
        }
        if (visibleRouter !== audioRouter) bottomNavigation.selectedItemId = R.id.music
        else super.onBackPressed()
    }

    private fun getVisibleRouter(): Router? {
        if (popularContainer.visibility == View.VISIBLE)
            return popularRouter
        if (roomContainer.visibility == View.VISIBLE)
            return roomRouter
        return audioRouter
    }

    private var isAfterPopular: Boolean = false
    @Suppress("DEPRECATION")
    private val navigationListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val isReselected = bottomNavigation.selectedItemId == item.itemId
        when (item.itemId) {
            R.id.music -> {
                mainPresenter.showContainer(audioContainer, isReselected)
                if (isAfterPopular) {
                    changeIcon()
                    isAfterPopular = false
                }
            }
            R.id.popular -> {
                if (!isAfterPopular) {
                    changeIcon()
                }
                isAfterPopular = true
                mainPresenter.showContainer(popularContainer, isReselected)
            }
            R.id.room -> {
                mainPresenter.showContainer(roomContainer, isReselected)
                if (isAfterPopular) {
                    changeIcon()
                    isAfterPopular = false
                }
            }
        }
        true
    }

    private fun changeIcon() {
        val view = findViewById(R.id.popular)
        view.animate()?.alpha(0f)?.duration = delay
        view.animate().alpha(0f).withEndAction {
            bottomNavigation.menu.findItem(R.id.popular).icon = resources.getDrawable(
                    if (isAfterPopular) R.drawable.search else R.drawable.fire)
            bottomNavigation.menu.findItem(R.id.popular).title =
                    if (isAfterPopular) "Поиск" else "Популярное"
            view.animate()?.alpha(1f)?.duration = delay
            view.animate()?.alpha(1f)?.duration = delay
        }?.duration = delay
    }

    override fun showContainer(container: ChangeHandlerFrameLayout?, isReselected: Boolean) {
        audioContainer.visibility = if (container == null || container.id == R.id.audioContainer)
            View.VISIBLE else View.INVISIBLE
        popularContainer.visibility = if (container?.id == R.id.popularContainer)
            View.VISIBLE else View.INVISIBLE
        roomContainer.visibility = if (container?.id == R.id.roomContainer)
            View.VISIBLE else View.INVISIBLE

        if (!isReselected) {
            container?.alpha = 0F
            container?.animate()?.alpha(1F)
            if (container?.id == R.id.roomContainer) {
                isRoom = true
                roomPreviewInclude.visibility = View.INVISIBLE
                playerPreviewInclude.animate().alpha(0.5f)
                        .withEndAction({
                            roomPreviewInclude.alpha = 0.5f
                            playerPreviewInclude.visibility = INVISIBLE
                            roomPreviewInclude.visibility = View.VISIBLE
                            roomPreviewInclude.animate().scaleX(1f).duration = delay
                            roomPreviewInclude.animate().scaleY(1f).duration = delay
                            roomPreviewInclude.animate().alpha(1f).duration = delay
                        }).duration = delay
            } else if (isRoom) {
                isRoom = false
                playerPreviewInclude.visibility = View.INVISIBLE
                roomPreviewInclude.animate().alpha(0.5f)
                        .withEndAction({
                            playerPreviewInclude.alpha = 0.5f
                            roomPreviewInclude.visibility = INVISIBLE
                            playerPreviewInclude.visibility = View.VISIBLE
                            playerPreviewInclude.animate().scaleX(1f).duration = delay
                            playerPreviewInclude.animate().scaleY(1f).duration = delay
                            playerPreviewInclude.animate().alpha(1f).duration = delay
                        }).duration = delay
            }
        } else if (container?.id == R.id.popularContainer && !isSearch) {
            isSearch = true
            searchPreviewInclude.visibility = VISIBLE
            searchEditText.showKeyboard()
            searchPreviewInclude.alpha = 0f
            searchPreviewInclude.translationY = -searchPreviewInclude.height.toFloat()
            searchPreviewInclude.animate().translationY(0f).startDelay = keyboardDelay
            searchPreviewInclude.animate().alpha(1f).startDelay = keyboardDelay
            playerPreviewInclude.animate()
                    .translationY(searchPreviewInclude.height.toFloat())
                    .withEndAction({ playerPreviewInclude.visibility = INVISIBLE })
                    .startDelay = keyboardDelay
            bottomNavigation.visibility = INVISIBLE
        }
    }

    override fun showAuthController() {
        getVisibleRouter()?.setRoot(RouterTransaction.with(AuthController())
                .pushChangeHandler(HorizontalChangeHandler())
                .popChangeHandler(HorizontalChangeHandler()))
    }

    override fun onPanelStateChanged(panel: View?, previousState: SlidingUpPanelLayout.PanelState?,
                                     newState: SlidingUpPanelLayout.PanelState?) {
        main.playerController.onPanelStateChanged(newState, isRoom)
    }

    override fun onPanelSlide(panel: View?, slideOffset: Float) {
        main.playerController.onPanelSlide(slideOffset)
    }

    fun resetSlidingPanel() = Handler().postDelayed({
        bottomNavigation.visibility = VISIBLE
        slidingLayout.panelHeight = resources!!.getDimension(R.dimen.navigation).toInt()
    }, 200)

}

