package com.geronymo.checkmate.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.geronymo.checkmate.R
import com.geronymo.checkmate.data.viewmodels.UserViewModel
import com.geronymo.checkmate.ui.theme.CheckMateTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@ExperimentalMaterial3Api
@Composable
fun SplashScreen(navController: NavController) {
    val userViewModel = UserViewModel()

    LaunchedEffect(Unit) {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser == null) {
            navController.navigate("SignIn") {
                popUpTo(navController.graph.id) { inclusive = true }
            }
        }  else {
            if (!firebaseUser.isEmailVerified) {
                navController.navigate("VerifyEmail") {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        }
        userViewModel.getUser()
        userViewModel.user.collect { user ->
            if (user != null) {
                if (user.username?.isEmpty() == true) {
                    navController.navigate("SetUsername") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                } else {
                    navController.navigate("Home") {
                        popUpTo(navController.graph.id) { inclusive = true }
                    }
                }
            }
        }

    }

    CheckMateTheme() {
        Scaffold() { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painterResource(id = R.drawable.checklist),
                        contentDescription = "Todos",
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .padding(top = 150.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    CircularProgressIndicator(modifier = Modifier.padding(top = 20.dp))
                }
            }
        }
    }
}