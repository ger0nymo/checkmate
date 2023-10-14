package com.geronymo.checkmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geronymo.checkmate.ui.screens.SigninScreen
import com.geronymo.checkmate.ui.screens.SignupScreen
import com.geronymo.checkmate.ui.screens.SplashScreen
import com.geronymo.checkmate.ui.screens.VerifyEmailSceen
import com.geronymo.checkmate.ui.theme.CheckMateTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckMateTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "VerifyEmail"
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
                        SigninScreen(navController, this@MainActivity)
                    }
                    composable(route = "SignUp", enterTransition = {
                        slideIntoContainer(
                            AnimatedContentTransitionScope.SlideDirection.Right,
                            tween(500)
                        )
                    }) {
                        SignupScreen(navController)
                    }
                    composable(route = "Splash") {
                        SplashScreen()
                    }
                    composable(route = "VerifyEmail") {
                        VerifyEmailSceen()
                    }
                }
            }
        }
    }
}