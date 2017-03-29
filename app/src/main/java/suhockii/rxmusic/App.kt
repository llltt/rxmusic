package suhockii.rxmusic

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary

/** Created by Maksim Sukhotski on 3/25/2017.*/

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
        val initializerBuilder = Stetho.newInitializerBuilder(this)
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this)
        )
        initializerBuilder.enableDumpapp(
                Stetho.defaultDumperPluginsProvider(this)
        )
        val initializer = initializerBuilder.build()
        Stetho.initialize(initializer)
    }
}