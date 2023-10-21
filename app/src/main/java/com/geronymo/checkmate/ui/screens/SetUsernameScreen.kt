package com.geronymo.checkmate.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.geronymo.checkmate.R
import com.geronymo.checkmate.data.viewmodels.SetUsernameViewModel
import com.geronymo.checkmate.data.viewmodels.SignUpViewModel
import com.geronymo.checkmate.data.viewmodels.UserViewModel
import com.geronymo.checkmate.ui.components.CMAOutlinedButton
import com.geronymo.checkmate.ui.components.CMATextField
import com.geronymo.checkmate.ui.theme.CheckMateTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetUsernameScreen(navController: NavController) {
    val viewModel: SetUsernameViewModel = viewModel()

    val usernameState = viewModel.usernameState.collectAsState()
    val usernameValidationState = viewModel.usernameValidationState.collectAsState()
    val successState = viewModel.success.collectAsState()
    val buttonLoadingState = viewModel.buttonLoading.collectAsState()

    LaunchedEffect(successState.value) {
        if (successState.value) {
            navController.navigate("Home") {
                popUpTo("SetUsername") { inclusive = true }
            }
        }
    }

    CheckMateTheme() {
        Scaffold() { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painterResource(id = R.drawable.username),
                        contentDescription = "Todos",
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .padding(top = 80.dp)
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Seems like you're new here!", style = MaterialTheme.typography.headlineSmall)
                }
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .padding(top = 16.dp)
                ) {
                    Text(text = "Please set a username and press the button below to continue", style = MaterialTheme.typography.titleMedium)
                }
                CMATextField(value = usernameState.value, onValueChange = {str -> viewModel.setUsername(str)}, label = "Username", type = KeyboardType.Text, modifier = Modifier.padding(top = 36.dp),
                    isError = !usernameValidationState.value.isValid,
                    errorMessage = "*${usernameValidationState.value.errorMessage}",
                    )
                Spacer(modifier = Modifier.weight(0.80f))
                CMAOutlinedButton(
                    text = "Continue",
                    onClick = {
                        viewModel.setUsernameInDatabase()
                    },
                    enabled = usernameValidationState.value.isValid && usernameState.value.isNotEmpty() && !buttonLoadingState.value,
                    )
            }
        }
    }
}