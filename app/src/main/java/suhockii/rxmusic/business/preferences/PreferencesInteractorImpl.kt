package suhockii.rxmusic.business.preferences

import suhockii.rxmusic.App
import suhockii.rxmusic.data.repositories.auth.models.Credentials
import suhockii.rxmusic.data.repositories.preferences.PreferencesRepository
import javax.inject.Inject

/** Created by Maksim Sukhotski on 4/8/2017. */
class PreferencesInteractorImpl : PreferencesInteractor {

    @Inject lateinit var repository: PreferencesRepository

    init {
        App.appComponent.inject(this)
    }

    override fun saveCredentials(credentials: Credentials) {
        repository.credentials = credentials
    }

    override fun getCredentials(): Credentials {
        return repository.credentials
    }

    override fun isEmpty(): Boolean {
        return repository.empty
    }
}