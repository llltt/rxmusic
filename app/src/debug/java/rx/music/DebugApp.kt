package rx.music

/** Created by Maksim Sukhotski on 5/8/2017. */

import com.facebook.stetho.Stetho
import com.uphyca.stetho_realm.RealmInspectorModulesProvider

/** Created by Maksim Sukhotski on 3/25/2017.*/

class DebugApp : App() {
    override fun onCreate() {
        super.onCreate()
        Stetho.initialize(Stetho.newInitializerBuilder(this)
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .build())
    }
}
