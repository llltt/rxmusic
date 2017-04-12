package suhockii.rxmusic.smth

import android.os.Build

/** Created by Maksim Sukhotski on 4/10/2017. */
fun isLollipopOrAbove(func: () -> Unit) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        func()
    }
}

fun runAsync(func: () -> Unit) {
    Thread(Runnable { func() }).start()
}