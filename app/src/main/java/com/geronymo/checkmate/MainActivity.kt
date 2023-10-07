package com.geronymo.checkmate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.geronymo.checkmate.ui.screens.LoginScreen
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
                    startDestination = "loginScreen"
                ) {
                    composable("loginScreen") {
                        LoginScreen(navController)
                    }
                    composable("profileScreen") {
                        ProfileScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(navController: NavController) {
    Column {
        Button(onClick = {
            navController.navigate("homeScreen")
        }) {
            Text(text = "Click me to go to the home screen")
        }
        Text(text = "This is the profile screen")
    }
}