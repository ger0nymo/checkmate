package com.geronymo.checkmate.ui.screens.signin

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
import com.geronymo.checkmate.ui.components.CMAIconButton
import com.geronymo.checkmate.ui.components.CMAOutlinedButton
import com.geronymo.checkmate.ui.components.CMATextButton
import com.geronymo.checkmate.ui.components.CMATextField
import com.geronymo.checkmate.ui.theme.CheckMateTheme

@ExperimentalMaterial3Api
@Composable
fun SigninScreen(navController: NavController) {
    val viewModel: SignInViewModel = viewModel()

    val emailState = viewModel.emailState.collectAsState()
    val passwordState = viewModel.passwordState.collectAsState()

    val emailValidationState = viewModel.emailValidationState.collectAsState()
    val passwordValidationState = viewModel.passwordValidationState.collectAsState()

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
                        painterResource(id = R.drawable.checklist),
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
                        value = passwordState.value,
                        onValueChange = { str -> viewModel.setPassword(str) },
                        label = "Password",
                        type = KeyboardType.Password,
                        isError = !passwordValidationState.value.isValid,
                        errorMessage = "*${passwordValidationState.value.errorMessage}",
                    )
                    Spacer(modifier = Modifier.height(14.dp)) // If I add padding for the "Sign in" button it will be cut off TODO: fix this
                    CMAOutlinedButton(
                        onClick = {
                            viewModel.signIn()
                        },
                        text = "Sign in",
                        modifier = Modifier
                            .defaultMinSize(minHeight = 44.dp),

                        )
                    Spacer(modifier = Modifier.weight(1f))
                    CMAIconButton(
                        text = "Sign in via Google",
                        onClick = { /*TODO*/ },
                        drawableIconId = R.drawable.google,
                    )
                    CMATextButton(
                        text = "Don't have an account? Tap here",
                        onClick = {
                            navController.navigate("SignUp")
                        },
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}