package com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alerdoci.marvelsuperheroes.app.common.utils.PreferenceUtil
import com.alerdoci.marvelsuperheroes.app.common.utils.ThemeMode
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferenceUtil: PreferenceUtil
) : ViewModel() {

    private val _theme = MutableLiveData(ThemeMode.Dark)
    val theme: LiveData<ThemeMode> = _theme

    fun setTheme(newTheme: ThemeMode) {
        _theme.postValue(newTheme)
        preferenceUtil.putInt(PreferenceUtil.APP_THEME_INT, newTheme.ordinal)
    }

    fun getThemeValue(): ThemeMode {
        val savedThemeOrdinal = preferenceUtil.getInt(
            PreferenceUtil.APP_THEME_INT, ThemeMode.Auto.ordinal
        )
        return ThemeMode.entries.toTypedArray().getOrElse(savedThemeOrdinal) { ThemeMode.Auto }
    }

    fun getCurrentTheme(isSystemInDarkTheme: Boolean): ThemeMode {
        return when (val currentTheme = getThemeValue()) {
            ThemeMode.Auto -> if (isSystemInDarkTheme) ThemeMode.Dark else ThemeMode.Light
            else -> currentTheme
        }
    }
}
