package rx.music.di.modules

import dagger.Module
import dagger.Provides
import rx.music.business.auth.AuthInteractor
import rx.music.business.auth.AuthInteractorImpl
import rx.music.data.network.Retrofit
import rx.music.data.network.apis.AuthApi
import rx.music.data.repositories.auth.AuthRepository
import rx.music.data.repositories.auth.AuthRepositoryImpl
import rx.music.di.scopes.AuthScope

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