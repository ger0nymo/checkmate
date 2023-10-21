package com.geronymo.checkmate.ui.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geronymo.checkmate.data.viewmodels.FeedViewModel
import com.geronymo.checkmate.data.viewmodels.HomeViewModel
import com.geronymo.checkmate.ui.components.BottomNavigation
import com.geronymo.checkmate.ui.components.CMAFeedComponent
import com.geronymo.checkmate.ui.components.CMATopBar
import com.geronymo.checkmate.ui.theme.CheckMateTheme
import com.geronymo.checkmate.utils.enums.ScreenNameEnum
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen() {
    val homeViewModel = HomeViewModel()
    val feedViewModel = FeedViewModel()

    val systemUiController = rememberSystemUiController()

    val bgColor = MaterialTheme.colorScheme.surfaceTint
    val bottomNavBarColor = MaterialTheme.colorScheme.scrim

    val currentScreen = homeViewModel.currentScreen.collectAsState()
    val currentScreenTitle = homeViewModel.currentScreenTitle.collectAsState()

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
                BottomNavigation("Feeds") {
                    homeViewModel.setCurrentScreen(it)
                }
            },
            floatingActionButton = {
                if (currentScreen.value == ScreenNameEnum.PROFILE) {
                    ExtendedFloatingActionButton(
                        onClick = { /*TODO*/ },
                        text = { Text("New todo") },
                        icon = { Icon(Icons.Default.Add, contentDescription = "New todo") },
                    )
                }
            },
            topBar = {
                CMATopBar(title = currentScreenTitle.value)
            },

        ) { innerPadding ->
            AnimatedVisibility(
                visible = currentScreen.value == ScreenNameEnum.FEEDS,
                enter = fadeIn(animationSpec = tween(30, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(30, easing = LinearEasing))
            ) {
                FeedsScreen(feedViewModel, innerPadding)
            }

            AnimatedVisibility(
                visible = currentScreen.value == ScreenNameEnum.SEARCH,
                enter = fadeIn(animationSpec = tween(30, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(30, easing = LinearEasing))
            ) {
                SearchScreen(innerPadding)
            }

            AnimatedVisibility(
                visible = currentScreen.value == ScreenNameEnum.PROFILE,
                enter = fadeIn(animationSpec = tween(30, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(30, easing = LinearEasing))
            ) {
                ProfileScreen(innerPadding)
            }

        }
    }
}