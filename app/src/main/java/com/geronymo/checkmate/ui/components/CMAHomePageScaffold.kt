package com.geronymo.checkmate.ui.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMAHomePageScaffold() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Public", "Friends")
    val bgColor = MaterialTheme.colorScheme.surfaceTint

    val bottomNavBarColor = MaterialTheme.colorScheme.scrim

    var isSwipeToTheLeft by remember { mutableStateOf(false) }
    val dragState = rememberDraggableState(onDelta = { delta ->
        isSwipeToTheLeft = delta > 0
    })

    var swipedIndicatorWidthPercentage by remember { mutableStateOf(1f) }

    Scaffold(
        containerColor = bgColor,
        bottomBar = {
            BottomNavigation("Home")
        },
        // Create a TopAppBar with a search icon on the left, title in center and settings icon on right
        topBar = {
            CMATopBar(title = "Feeds")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .draggable(
                    state = dragState,
                    orientation = Orientation.Horizontal,
                    onDragStarted = { Log.d("HomeScreen", "Drag started") },
                    onDragStopped = {
                        if (isSwipeToTheLeft) {
                            tabIndex = 0
                        } else {
                            tabIndex = 1
                        }
                    }
                )
            ,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            androidx.compose.material3.TabRow(
                selectedTabIndex = tabIndex,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                        color = MaterialTheme.colorScheme.onSurface,
                        height = 2.dp,

                    )
                },
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        modifier = androidx.compose.ui.Modifier
                            .background(bgColor),
                        selected = tabIndex == index,
                        onClick = {
                            tabIndex = index
                            Log.d("HomeScreen", "Tab index: $tabIndex")
                        },
                        text = { Text(title, color = MaterialTheme.colorScheme.onSurface) }
                    )
                }
            }
        }
    }
}