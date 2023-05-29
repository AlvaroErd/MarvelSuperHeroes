package com.alerdoci.marvelsuperheroes.app.screens.splash.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alerdoci.marvelsuperheroes.app.navigation.Screen
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.Home.route)
    val startDestination: State<String> = _startDestination

    init {
        viewModelScope.launch {
            Thread.sleep(3000)
            _startDestination.value = Screen.Home.route
        }
        _isLoading.value = false
    }
}