package com.geronymo.checkmate.ui.screens

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.geronymo.checkmate.MainActivity
import com.geronymo.checkmate.R
import com.geronymo.checkmate.data.viewmodels.SignInViewModel
import com.geronymo.checkmate.data.viewmodels.UserViewModel
import com.geronymo.checkmate.ui.components.CMAIconButton
import com.geronymo.checkmate.ui.components.CMAOutlinedButton
import com.geronymo.checkmate.ui.components.CMATextButton
import com.geronymo.checkmate.ui.components.CMATextField
import com.geronymo.checkmate.ui.theme.CheckMateTheme
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@ExperimentalMaterial3Api
@Composable
fun SigninScreen(navController: NavController, activity: MainActivity) {
    val viewModel: SignInViewModel = viewModel()
    val userViewModel: UserViewModel = viewModel()

    val emailState = viewModel.emailState.collectAsState()
    val passwordState = viewModel.passwordState.collectAsState()

    val emailValidationState = viewModel.emailValidationState.collectAsState()
    val passwordValidationState = viewModel.passwordValidationState.collectAsState()

    val userState = userViewModel.user.collectAsState()

    val googleSignInClient = viewModel.getGoogleSignInClient(activity)
    val startForResult =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            viewModel.signInWithGoogle(result, navController)
        }

    var googleSignInLoading: Boolean by remember { mutableStateOf(false) }
    var emailSignInLoading: Boolean by remember { mutableStateOf(false) }

    CheckMateTheme() {
        Scaffold(
        ) { innerPadding ->
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
                        painterResource(id = R.drawable.signin),
                        contentDescription = "Todos",
                        modifier = Modifier
                            .fillMaxWidth(0.45f)
                            .padding(top = 50.dp)
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
                            viewModel.signInWithEmailAndPassword(navController)
                            val canSignIn =
                                passwordValidationState.value.isValid && emailValidationState.value.isValid
                            if (canSignIn) {
                                emailSignInLoading = true
                                Handler(Looper.getMainLooper()).postDelayed({
                                    emailSignInLoading = false
                                }, 3000)
                            }
                        },
                        enabled = !emailSignInLoading,
                        text = "Sign in",
                        modifier = Modifier
                            .defaultMinSize(minHeight = 44.dp),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    CMAIconButton(
                        text = "Sign in via Google",
                        onClick = {
                            if (userState.value == null) {
                                googleSignInLoading = true
                                Log.d("SignInScreen", "RUNS")
                                val signInIntent = googleSignInClient.signInIntent
                                startForResult.launch(signInIntent)
                                Handler(Looper.getMainLooper()).postDelayed({
                                    googleSignInLoading = false
                                }, 5000)
                            }
                        },
                        enabled = !googleSignInLoading,
                        drawableIconId = R.drawable.google,
                    )
                    CMATextButton(
                        text = "Don't have an account? Tap here",
                        onClick = {
                            val auth: FirebaseAuth = Firebase.auth
                            auth.signOut()
                            navController.navigate("SignUp")
                        },
                        modifier = Modifier.padding(top = 12.dp)
                    )
                }
            }
        }
    }
}
