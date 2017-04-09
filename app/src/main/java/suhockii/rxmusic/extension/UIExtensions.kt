package suhockii.rxmusic.extension

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.View
import suhockii.rxmusic.ui.base.MoxyController

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

inline val RecyclerView.ViewHolder.context
    get() = this.itemView.context!!

fun Context.isNetworkConnected() = (this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null

