package me.ext

/** Created by Maksim Sukhotski on 4/10/2017. */
fun isLollipopOrAbove(func: () -> Unit) {
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
        func()
    }
}

fun runAsync(func: () -> Unit) {
    Thread(Runnable { func() }).start()
}