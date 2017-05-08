package rx.music

/** Created by Maksim Sukhotski on 5/8/2017. */

import android.app.Application
import com.facebook.stetho.Stetho
import com.squareup.leakcanary.LeakCanary
import com.uphyca.stetho_realm.RealmInspectorModulesProvider
import rx.music.App.Companion.refWatcher
import rx.music.dagger.Dagger

/** Created by Maksim Sukhotski on 3/25/2017.*/

class DebugApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build())
        if (!LeakCanary.isInAnalyzerProcess(this)) refWatcher = LeakCanary.install(this)
        Dagger(this)
    }
}
