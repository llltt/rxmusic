package suhockii.rxmusic

import android.app.Application
import com.facebook.stetho.Stetho
import com.frogermcs.androiddevmetrics.AndroidDevMetrics
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import suhockii.rxmusic.data.dagger.AppComponent
import suhockii.rxmusic.data.dagger.DaggerAppComponent
import suhockii.rxmusic.data.dagger.RepositoriesModule

/** Created by Maksim Sukhotski on 3/25/2017.*/

class App : Application() {

    companion object {
        lateinit var refWatcher: RefWatcher
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
                .repositoriesModule(RepositoriesModule(this))
                .build()
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this)
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .build())
            if (!LeakCanary.isInAnalyzerProcess(this)) refWatcher = LeakCanary.install(this)
        }
    }
}