package com.geronymo.checkmate.ui.screens

import android.graphics.drawable.Icon
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.twotone.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.geronymo.checkmate.ui.components.BottomNavigation
import com.geronymo.checkmate.ui.components.CMAFeedComponent
import com.geronymo.checkmate.ui.components.CMATopBar
import com.geronymo.checkmate.ui.theme.CheckMateTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import org.checkerframework.common.subtyping.qual.Bottom

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun HomeScreen() {
    var tabIndex by remember { mutableStateOf(0) }

    val tabs = listOf("Public", "Friends")

    val systemUiController = rememberSystemUiController()

    val bgColor = MaterialTheme.colorScheme.surfaceTint
    val bottomNavBarColor = MaterialTheme.colorScheme.scrim

    var isSwipeToTheLeft by remember { mutableStateOf(false) }
    val dragState = rememberDraggableState(onDelta = { delta ->
        isSwipeToTheLeft = delta > 0
    })

    DisposableEffect(systemUiController) {
        systemUiController.setStatusBarColor(
            color = bgColor
        )
        systemUiController.setNavigationBarColor(
            color = bottomNavBarColor
        )
        onDispose {  }
    }

    CheckMateTheme() {
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
                        onDragStarted = {},
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
                TabRow(
                    selectedTabIndex = tabIndex,
                    indicator = { tabPositions ->
                        TabRowDefaults.Indicator(
                            Modifier.tabIndicatorOffset(tabPositions[tabIndex]),
                            color = MaterialTheme.colorScheme.onSurface,
                            height = 2.dp
                        )
                    },
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            modifier = Modifier
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
                Crossfade(targetState = tabIndex, label = "") { index ->
                    when (index) {
                        0 -> AnimatedVisibility(
                            visible = tabIndex == 0,
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
                            visible = tabIndex == 1,
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
    }
}