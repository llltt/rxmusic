package rx.music

import android.app.Application
import com.squareup.leakcanary.RefWatcher
import rx.music.dagger.Dagger
import rx.music.dagger.components.AppComponent

/** Created by Maksim Sukhotski on 3/25/2017.*/

class App : Application() {

    companion object {
        lateinit var instance: App private set
        lateinit var refWatcher: RefWatcher
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        Dagger(this)
    }
}
