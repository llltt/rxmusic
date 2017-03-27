package suhockii.rxmusic

import android.app.Application
import com.squareup.leakcanary.LeakCanary

/** Created by Maksim Sukhotski on 3/25/2017.*/

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }
}