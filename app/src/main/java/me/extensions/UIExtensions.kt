package me.extensions

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import me.base.MoxyController
import rx.music.ui.main.MainActivity

@Suppress("UNCHECKED_CAST")
fun <T> Activity.findView(id: Int): Lazy<T> = lazy { this.findViewById(id) as T }

@Suppress("UNCHECKED_CAST")
fun <T> View.findView(id: Int): Lazy<T> = lazy { this.findViewById(id) as T }

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.findView(id: Int): Lazy<T> = lazy { this.view!!.findViewById(id) as T }

@Suppress("UNCHECKED_CAST")
fun <T> MoxyController.findView(id: Int): T = view!!.findViewById(id) as T

fun View.onClick(l: (v: View?) -> Unit) {
    setOnClickListener(l)
}

fun View.onLongClick(l: (v: View?) -> Boolean) {
    setOnLongClickListener(l)
}

fun View.setEnable(b: Boolean) {
    this.isEnabled = b
    if (this is ViewGroup) {
        val group = this

        for (idx in 0..group.childCount - 1) {
            enableDisableView(group.getChildAt(idx), b)
        }
    }
}

fun enableDisableView(view: View, enabled: Boolean) {

}

inline val RecyclerView.ViewHolder.context
    get() = this.itemView.context!!

fun View.hideKeyboard() {
    val im = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(this.windowToken, 0)
}

fun View.showKeyboard() {
    val im = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Activity.toMain(): MainActivity = this as MainActivity



