package suhockii.rxmusic.dagger.component

import dagger.Component
import suhockii.rxmusic.dagger.module.AppModule
import suhockii.rxmusic.dagger.module.AuthModule
import suhockii.rxmusic.dagger.module.UserModule
import javax.inject.Singleton

/** Created by Maksim Sukhotski on 4/7/2017. */
@Component(modules = arrayOf(AppModule::class))
@Singleton interface AppComponent {

    fun plus(m: UserModule): UserComponent
    fun plus(m: AuthModule): AuthComponent
}