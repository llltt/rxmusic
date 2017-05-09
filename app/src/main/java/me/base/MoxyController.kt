package me.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpDelegate
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import com.squareup.leakcanary.RefWatcher
import io.realm.Realm
import rx.music.App
import rx.music.BuildConfig
import javax.inject.Inject


/** Created by Maksim Sukhotski on 4/6/2017. */
abstract class MoxyController : Controller {
    @Inject lateinit var refWatcher: RefWatcher
    @Inject lateinit protected var realm: Realm

    init {
        @Suppress("LeakingThis") App.appComponent.inject(this)
    }

    val mvpDelegate by lazy { MvpDelegate(this) }
    private var isStateSaved: Boolean = false
    private var hasExited: Boolean = false

    constructor() : super() {
        this.mvpDelegate.onCreate()
    }

    constructor(args: Bundle?) : super(args) {
        this.mvpDelegate.onCreate(args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container)
        onViewBound(view)
        return view
    }

    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View

    protected abstract fun onViewBound(view: View)

    override fun onAttach(view: View) {
        super.onAttach(view)
        mvpDelegate.onAttach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        mvpDelegate.onDetach()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        mvpDelegate.onDetach()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (hasExited && BuildConfig.DEBUG) refWatcher.watch(this)
        if (isStateSaved) return
        mvpDelegate.onDestroy()
        realm.close()
    }

    override fun onRestoreViewState(view: View, savedViewState: Bundle) {
        super.onRestoreViewState(view, savedViewState)
        mvpDelegate.onDestroyView()
        mvpDelegate.onCreate(savedViewState)
    }

    override fun onSaveViewState(view: View, outState: Bundle) {
        super.onSaveViewState(view, outState)
        isStateSaved = true
        mvpDelegate.onSaveInstanceState(outState)
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        hasExited = !changeType.isEnter
        if (isDestroyed && BuildConfig.DEBUG) refWatcher.watch(this)
    }
}