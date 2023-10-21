package com.geronymo.checkmate.data.viewmodels

import androidx.lifecycle.ViewModel
import com.geronymo.checkmate.utils.enums.ScreenNameEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {
    private val _currentScreen = MutableStateFlow(ScreenNameEnum.FEEDS)
    val currentScreen: StateFlow<ScreenNameEnum> = _currentScreen

    private val _currentScreenTitle = MutableStateFlow(currentScreen.value.name)
    val currentScreenTitle: StateFlow<String> = _currentScreenTitle

    init {
        changeCurrentScreenTitle()
    }

    fun setCurrentScreen(screen: ScreenNameEnum) {
        _currentScreen.value = screen
        changeCurrentScreenTitle()
    }

    fun changeCurrentScreenTitle() {
        _currentScreenTitle.value = currentScreen.value.name.lowercase().replaceFirstChar {
            if (it.isLowerCase()) it.titlecase() else it.toString()
        }
    }
}