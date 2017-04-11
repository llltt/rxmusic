package suhockii.rxmusic.ui.base

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.MvpDelegate
import com.bluelinelabs.conductor.Controller
import com.bluelinelabs.conductor.ControllerChangeHandler
import com.bluelinelabs.conductor.ControllerChangeType
import suhockii.rxmusic.App


/** Created by Maksim Sukhotski on 4/6/2017. */
abstract class MoxyController : Controller {

    val mvpDelegate by lazy { MvpDelegate(this) }
    var isStateSaved = false
    private var hasExited: Boolean = false
    protected abstract fun inflateView(inflater: LayoutInflater, container: ViewGroup): View
    protected abstract fun onViewBound(view: View)
    protected abstract fun setupControllerComponent()

    constructor() : super() {
        this.mvpDelegate.onCreate()
    }

    constructor(args: Bundle?) : super(args) {
        this.mvpDelegate.onCreate(args)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup): View {
        val view = inflateView(inflater, container)
        setupControllerComponent()
        onViewBound(view)
        return view
    }

    override fun onChangeEnded(changeHandler: ControllerChangeHandler, changeType: ControllerChangeType) {
        super.onChangeEnded(changeHandler, changeType)
        hasExited = !changeType.isEnter
        if (isDestroyed) App.refWatcher.watch(this)
    }

    private fun getActionBar(): ActionBar {
        val actionBarProvider = activity as ActionBarProvider
        return actionBarProvider.getSupportActionBar()!!
    }

    protected fun setTitle() {
        var parentController = parentController
        while (parentController != null) {
            if (parentController is MoxyController && parentController.getTitle() != null) return
            parentController = parentController.parentController
        }

        val title = getTitle()
        val actionBar = getActionBar()
        if (title != null) getActionBar().title = title
    }

    protected open fun getTitle(): String? {
        return null
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

    override fun onDestroy() {
        super.onDestroy()
        if (hasExited) App.refWatcher.watch(this)
        if (isStateSaved) return
        mvpDelegate.onDestroy()
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        mvpDelegate.onDetach()
    }

    override fun onDetach(view: View) {
        super.onDetach(view)
        mvpDelegate.onDetach()
    }

    override fun onAttach(view: View) {
        setTitle()
        super.onAttach(view)
        mvpDelegate.onAttach()
    }


}