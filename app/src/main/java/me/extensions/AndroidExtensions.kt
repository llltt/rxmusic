package me.extensions

import android.os.Handler

/** Created by Maksim Sukhotski on 4/10/2017. */
fun isLollipopOrAbove(func: () -> Unit) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        func()
    }
}

fun runAsync(func: () -> Unit) {
    Thread(Runnable { func() }).start()
}

fun runAfter(func: () -> Unit) {
    Handler().postDelayed({
        func()
    }, 5)
}

fun tryCatchAsync(func: () -> Unit) {
    runAfter {
        runAsync {
            try {
                func()
            } catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }
}


fun tryCatch(func: () -> Unit) {
    runAfter {
        try {
            func()
        } catch(e: Exception) {
            e.printStackTrace()
        }
    }
}
