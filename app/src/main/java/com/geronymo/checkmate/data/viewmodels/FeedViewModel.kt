package com.geronymo.checkmate.data.viewmodels

import androidx.compose.foundation.gestures.DraggableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FeedViewModel : ViewModel() {
    private val _tabIndex: MutableStateFlow<Int> = MutableStateFlow(0)
    var tabIndex: StateFlow<Int> = _tabIndex

    private val _isSwipeToTheLeft: MutableStateFlow<Boolean> = MutableStateFlow(false)
    var isSwipeToTheLeft: StateFlow<Boolean> = _isSwipeToTheLeft

    fun setTabIndex(index: Int) {
        _tabIndex.value = index
    }

    fun updateSwipeDirection(delta: Float) {
        _isSwipeToTheLeft.value = delta > 0
    }
}