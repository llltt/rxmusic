package rx.music.business.preferences

import rx.music.data.net.models.Credentials
import rx.music.data.repositories.preferences.PreferencesRepository

/** Created by Maksim Sukhotski on 4/8/2017. */
class PreferencesInteractorImpl(private val repository: PreferencesRepository) : PreferencesInteractor {

    override fun saveCredentials(credentials: Credentials) {
        repository.credentials = credentials
    }

    override fun getCredentials(): Credentials {
        return repository.credentials
    }

    override fun authTokenEmpty(): Boolean {
        return repository.empty
    }
}