package rx.music

import android.app.Application
import io.realm.Realm
import rx.music.dagger.Dagger
import rx.music.dagger.components.AppComponent

/** Created by Maksim Sukhotski on 3/25/2017.*/

open class App : Application() {
    companion object {
        lateinit var instance: App private set
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Dagger(this)
        Realm.init(this)
    }
}
