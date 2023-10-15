package com.geronymo.checkmate.ui.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.geronymo.checkmate.MainActivity
import com.geronymo.checkmate.ui.screens.SigninScreen
import com.geronymo.checkmate.ui.screens.SignupScreen
import com.geronymo.checkmate.ui.screens.SplashScreen
import com.geronymo.checkmate.ui.screens.VerifyEmailSceen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMAAnimatedNavHost(navController: NavHostController, activity: MainActivity) {
    NavHost(
        navController = navController,
        startDestination = "Splash"
    ) {
        composable(
            route = "SignIn",
            enterTransition = {
                slideIntoContainer(
                    AnimatedContentTransitionScope.SlideDirection.Left,
                    tween(500)
                )

            }
        ) {
            SigninScreen(navController, activity)
        }
        composable(route = "SignUp", enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(500)
            )
        }) {
            SignupScreen(navController)
        }
        composable(route = "Splash", enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(500)
            )

        }) {
            SplashScreen(navController)
        }
        composable(route = "VerifyEmail", enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(500)
            )

        }) {
            VerifyEmailSceen()
        }
    }
}