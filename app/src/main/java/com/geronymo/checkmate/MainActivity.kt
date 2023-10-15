package com.geronymo.checkmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.rememberNavController
import com.geronymo.checkmate.ui.navigation.CMAAnimatedNavHost
import com.geronymo.checkmate.ui.theme.CheckMateTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CheckMateTheme {
                val navController = rememberNavController()
                CMAAnimatedNavHost(navController = navController, activity = this)
            }
        }
    }
}