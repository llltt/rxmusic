package rx.music

import android.app.Application
import com.facebook.stetho.Stetho
import com.frogermcs.androiddevmetrics.AndroidDevMetrics
import com.squareup.leakcanary.LeakCanary
import com.squareup.leakcanary.RefWatcher
import rx.music.di.components.AppComponent
import rx.music.di.components.AuthComponent
import rx.music.di.components.DaggerAppComponent
import rx.music.di.components.UserComponent
import rx.music.di.modules.AppModule
import rx.music.di.modules.AuthModule
import rx.music.di.modules.UserModule

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
