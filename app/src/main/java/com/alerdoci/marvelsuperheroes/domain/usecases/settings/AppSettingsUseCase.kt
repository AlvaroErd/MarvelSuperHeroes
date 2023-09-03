package com.alerdoci.marvelsuperheroes.domain.usecases.settings

import com.alerdoci.marvelsuperheroes.datasource.features.onboarding.cache.settings.DataStoreRepository
import javax.inject.Inject

class AppSettingsUseCase @Inject constructor(
    private val appPreferencesRepository: DataStoreRepository
) {

}
