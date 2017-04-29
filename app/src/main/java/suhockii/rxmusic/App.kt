package suhockii.rxmusic

import android.app.Application
import com.facebook.stetho.Stetho
import com.frogermcs.androiddevmetrics.AndroidDevMetrics
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import suhockii.rxmusic.dagger.components.AppComponent
import suhockii.rxmusic.dagger.components.AuthComponent
import suhockii.rxmusic.dagger.components.DaggerAppComponent
import suhockii.rxmusic.dagger.components.UserComponent
import suhockii.rxmusic.dagger.modules.AppModule
import suhockii.rxmusic.dagger.modules.AuthModule
import suhockii.rxmusic.dagger.modules.UserModule

/** Created by Maksim Sukhotski on 3/25/2017.*/

class App : Application() {

    companion object {
        lateinit var instance: App private set
        lateinit var refWatcher: RefWatcher
        lateinit var appComponent: AppComponent
    }

    var userComponent: UserComponent?
        get() = appComponent.plus(UserModule())
        set(value) {}

    var authComponent: AuthComponent?
        get() = appComponent.plus(AuthModule())
        set(value) {}

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            AndroidDevMetrics.initWith(this)
            Stetho.initialize(Stetho.newInitializerBuilder(this)
                    .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                    .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                    .build())
            if (!LeakCanary.isInAnalyzerProcess(this)) refWatcher = LeakCanary.install(this)
        }
        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()
    }
}
