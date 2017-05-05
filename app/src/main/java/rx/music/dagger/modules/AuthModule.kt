package rx.music.dagger.modules

import dagger.Module
import dagger.Provides
import rx.music.business.auth.AuthInteractor
import rx.music.business.auth.AuthInteractorImpl
import rx.music.dagger.scopes.AuthScope
import rx.music.data.auth.AuthRepository
import rx.music.data.auth.AuthRepositoryImpl
import rx.music.network.Retrofit
import rx.music.network.apis.AuthApi

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module class AuthModule {
    @Provides @AuthScope fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides @AuthScope fun provideAuthInteractor(): AuthInteractor {
        return AuthInteractorImpl()
    }

    @Provides @AuthScope fun provideAuthApi(): AuthApi {
        return Retrofit.build(AuthApi::class.java)
    }
}