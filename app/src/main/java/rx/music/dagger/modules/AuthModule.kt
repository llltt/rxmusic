package rx.music.dagger.modules

import dagger.Module
import dagger.Provides
import rx.music.business.auth.AuthInteractor
import rx.music.business.auth.AuthInteractorImpl
import rx.music.dagger.scopes.PerAuth
import rx.music.data.auth.AuthRepository
import rx.music.data.auth.AuthRepositoryImpl
import rx.music.net.Retrofit
import rx.music.net.apis.AuthApi

/** Created by Maksim Sukhotski on 4/8/2017. */
@Module class AuthModule {
    @Provides @PerAuth fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides @PerAuth fun provideAuthInteractor(): AuthInteractor {
        return AuthInteractorImpl()
    }

    @Provides @PerAuth fun provideAuthApi(): AuthApi {
        return Retrofit.build(AuthApi::class.java)
    }
}