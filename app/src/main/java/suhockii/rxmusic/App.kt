package suhockii.rxmusic

import android.app.Application
import com.facebook.stetho.Stetho
import com.frogermcs.androiddevmetrics.AndroidDevMetrics
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import suhockii.rxmusic.data.dagger.AppComponent
import suhockii.rxmusic.data.dagger.InteractorsModule
import suhockii.rxmusic.data.dagger.UserComponent

/** Created by Maksim Sukhotski on 3/25/2017.*/

class App : Application() {

    companion object {
        lateinit var refWatcher: RefWatcher
        lateinit var appComponent: AppComponent
        lateinit var userComponent: UserComponent
    }

    override fun onCreate() {
        super.onCreate()
//        appComponent = DaggerAppComponent.builder()
//                .repositoriesModule(RepositoriesModule(this))
//                .build()
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this)
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .build())
            if (!LeakCanary.isInAnalyzerProcess(this)) refWatcher = LeakCanary.install(this)
        }
    }

    fun createUserComponent(): UserComponent {
        // always get only one instance
        if (userComponent == null) {
            // start lifecycle of userComponent
            userComponent = appComponent.plus(InteractorsModule())
        }
        return userComponent
    }

    fun releaseUserComponent() {
        // end lifecycle of userComponent
        userComponent = null!!
    }
}