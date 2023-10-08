package com.geronymo.checkmate.ui.screens.signup

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.geronymo.checkmate.R
import com.geronymo.checkmate.ui.components.CMAOutlinedButton
import com.geronymo.checkmate.ui.components.CMATextButton
import com.geronymo.checkmate.ui.components.CMATextField
import com.geronymo.checkmate.ui.theme.CheckMateTheme

@ExperimentalMaterial3Api
@Composable
fun SignupScreen(navController: NavController) {
    val viewModel: SignUpViewModel = viewModel()

    val emailState = viewModel.emailState.collectAsState()
    val usernameState = viewModel.usernameState.collectAsState()
    val passwordState = viewModel.passwordState.collectAsState()
    val confirmPasswordState = viewModel.confirmPasswordState.collectAsState()

    val emailValidationState = viewModel.emailValidationState.collectAsState()
    val usernameValidationState = viewModel.usernameValidationState.collectAsState()
    val passwordValidationState = viewModel.passwordValidationState.collectAsState()
    val confirmPasswordValidationState = viewModel.confirmPasswordValidationState.collectAsState()

    CheckMateTheme() {
        Scaffold { innerPadding ->
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
                        painterResource(
                            id = R.drawable.checklist2
                        ),
                        contentDescription = "Todos",
                        modifier = Modifier
                            .fillMaxWidth(0.55f)
                            .padding(top = 70.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CMATextField(
                        value = emailState.value,
                        onValueChange = { str -> viewModel.setEmail(str) },
                        label = "E-mail address",
                        type = KeyboardType.Email,
                        isError = !emailValidationState.value.isValid,
                        errorMessage = "*${emailValidationState.value.errorMessage}",
                    )
                    CMATextField(
                        value = usernameState.value,
                        onValueChange = { str -> viewModel.setUsername(str) },
                        label = "Username",
                        type = KeyboardType.Text,
                        isError = !usernameValidationState.value.isValid,
                        errorMessage = "*${usernameValidationState.value.errorMessage}",
                    )
                    CMATextField(
                        value = passwordState.value,
                        onValueChange = { str -> viewModel.setPassword(str) },
                        label = "Password",
                        type = KeyboardType.Password,
                        isError = !passwordValidationState.value.isValid,
                        errorMessage = "*${passwordValidationState.value.errorMessage}",
                    )
                    CMATextField(
                        value = confirmPasswordState.value,
                        onValueChange = { str -> viewModel.setConfirmPassword(str) },
                        label = "Password again",
                        type = KeyboardType.Password,
                        isError = !confirmPasswordValidationState.value.isValid,
                        errorMessage = "*${confirmPasswordValidationState.value.errorMessage}",
                    )
                    Spacer(modifier = Modifier.height(14.dp)) // If I add padding for the "Sign up" button it will be cut off TODO: fix this
                    CMAOutlinedButton(
                        text = "Sign up",
                        onClick = { viewModel.signUp() },
                        modifier = Modifier
                            .defaultMinSize(minHeight = 44.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CMATextButton(
                        text = "Already have an account? Tap here",
                        onClick = { navController.navigate("SignIn") },
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}