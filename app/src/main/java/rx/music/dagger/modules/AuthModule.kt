package rx.music.dagger.modules

import dagger.Module
import dagger.Provides
import rx.music.business.auth.AuthInteractor
import rx.music.business.auth.AuthInteractorImpl
import rx.music.dagger.scopes.PerAuth
import rx.music.data.auth.AuthRepo
import rx.music.data.auth.AuthRepoImpl
import rx.music.net.Retrofit
import rx.music.net.apis.AuthApi

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module class AuthModule {
    @Provides @PerAuth fun provideAuthRepository(): AuthRepo = AuthRepoImpl()

    @Provides @PerAuth fun provideAuthInteractor(): AuthInteractor = AuthInteractorImpl()

    @Provides @PerAuth fun provideAuthApi(): AuthApi = Retrofit.build(AuthApi::class.java)
}