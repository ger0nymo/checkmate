package com.geronymo.checkmate.ui.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.geronymo.checkmate.data.viewmodels.FeedViewModel
import com.geronymo.checkmate.ui.components.CMAFeedComponent
import kotlinx.coroutines.flow.StateFlow

@Composable
fun FeedsScreen(feedViewModel: FeedViewModel, innerPadding: PaddingValues) {
    val tabIndex = feedViewModel.tabIndex.collectAsState()
    var isSwipeToTheLeft = feedViewModel.isSwipeToTheLeft.collectAsState()
    val dragState = rememberDraggableState(onDelta = { delta ->
        feedViewModel.updateSwipeDirection(delta)
    })
    val tabs = listOf("Public", "Friends")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .draggable(
                state = dragState,
                orientation = Orientation.Horizontal,
                onDragStarted = {},
                onDragStopped = {
                    if (isSwipeToTheLeft.value) {
                        feedViewModel.setTabIndex(0)
                    } else {
                        feedViewModel.setTabIndex(1)
                    }
                }
            )
        ,
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        TabRow(
            selectedTabIndex = tabIndex.value,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[tabIndex.value]),
                    color = MaterialTheme.colorScheme.onSurface,
                    height = 2.dp
                )
            },
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    modifier = androidx.compose.ui.Modifier
                        .background(MaterialTheme.colorScheme.surfaceTint),
                    selected = tabIndex.value == index,
                    onClick = {
                        feedViewModel.setTabIndex(index)
                        Log.d("HomeScreen", "Tab index: $tabIndex.value")
                    },
                    text = { Text(title, color = MaterialTheme.colorScheme.onSurface) }
                )
            }
        }
        Crossfade(targetState = tabIndex.value, label = "") { index ->
            when (index) {
                0 -> AnimatedVisibility(
                    visible = tabIndex.value == 0,
                    enter = slideInHorizontally(
                        initialOffsetX = { -it }
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { -it }
                    )
                ) {
                    CMAFeedComponent(posts = emptyList())
                }

                1 -> AnimatedVisibility(
                    visible = tabIndex.value == 1,
                    enter = slideInHorizontally(
                        initialOffsetX = { it }
                    ),
                    exit = slideOutHorizontally(
                        targetOffsetX = { it }
                    )
                ) {
                    CMAFeedComponent(posts = emptyList())
                }
            }
        }
    }
}