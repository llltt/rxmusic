package suhockii.rxmusic.extension

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.net.ConnectivityManager
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import java.util.*

@Suppress("UNCHECKED_CAST")
fun <T> Activity.findView(id: Int): Lazy<T> = lazy { this.findViewById(id) as T }

@Suppress("UNCHECKED_CAST")
fun <T> View.findView(id: Int): Lazy<T> = lazy { this.findViewById(id) as T }

@Suppress("UNCHECKED_CAST")
fun <T> Fragment.findView(id: Int): Lazy<T> = lazy { this.view!!.findViewById(id) as T }

val Activity.view: View? get() = window.decorView.rootView

fun View.onClick(l: (v: View?) -> Unit) {
    setOnClickListener(l)
}

fun View.onLongClick(l: (v: View?) -> Boolean) {
    setOnLongClickListener(l)
}

inline val RecyclerView.ViewHolder.context
    get() = this.itemView.context!!

fun Context.isNetworkConnected() = (this.getSystemService(android.content.Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo != null

fun Context.isIntentAvailable(action: String): Boolean {
    return this.packageManager.queryIntentActivities(Intent(action), PackageManager.MATCH_DEFAULT_ONLY).size > 0
}

fun Context.hideKeyboard(view: View) {
    val im = this.getSystemService(android.content.Context.INPUT_METHOD_SERVICE) as InputMethodManager
    im.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Random.nextString(length: Int = 256, characters: String = "абвгдеёжзийклмнопрстуфхцчшщъыьэюя АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"): String {
    val text = CharArray(length)
    for (i in 0..length - 1) {
        text[i] = characters[(this.nextInt(characters.length))]
    }
    return String(text)
}

/**
 * Convert dp to pixel
 */
val Int.dp: Int
    get() = (this * Resources.getSystem().displayMetrics.density).toInt()


//fun AppCompatActivity.startFragment(fragment: android.app.Fragment, idContainer: Int = R.id.container) {
//    val tr = fragmentManager.beginTransaction()
//    tr.replace(idContainer, fragment)
//    tr.commit()
//}
//
//fun AppCompatActivity.startFragment(fragment: Fragment, idContainer: Int = R.id.container) {
//    val tr = supportFragmentManager.beginTransaction()
//    tr.replace(idContainer, fragment)
//    tr.commit()
//}
//
//fun Activity.startFragment(fragment: android.app.Fragment, idContainer: Int = R.id.container) {
//    val tr = fragmentManager.beginTransaction()
//    tr.replace(idContainer, fragment)
//    tr.commit()
//}

fun toast(context: Context, message: CharSequence) = Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun SwipeRefreshLayout.setRefresh(refresh: Boolean) {
    if (refresh) {
        this.isRefreshing = refresh
    } else {
        this.post { this.isRefreshing = refresh }
    }
}