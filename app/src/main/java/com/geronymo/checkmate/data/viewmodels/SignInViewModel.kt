package com.geronymo.checkmate.data.viewmodels

import android.app.Activity
import android.content.ContentValues.TAG
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geronymo.checkmate.R
import com.geronymo.checkmate.utils.InputValidator
import com.geronymo.checkmate.utils.ValidationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel() : ViewModel() {
    private val _emailState = MutableStateFlow("")
    private val _passwordState = MutableStateFlow("")
    private val _emailValidationState = MutableStateFlow(ValidationResult(true, ""))
    private val _passwordValidationState = MutableStateFlow(ValidationResult(true, ""))

    val emailState: StateFlow<String> = _emailState
    val passwordState: StateFlow<String> = _passwordState
    val emailValidationState: StateFlow<ValidationResult> = _emailValidationState
    val passwordValidationState: StateFlow<ValidationResult> = _passwordValidationState

    fun setEmail(email: String) {
        _emailState.value = email
    }

    fun setPassword(password: String) {
        _passwordState.value = password
    }

    fun validate() {
        _emailValidationState.value = InputValidator.validateEmail(emailState.value)
        _passwordValidationState.value = InputValidator.validatePassword(passwordState.value)
    }

    fun signInWithEmailAndPassword() {
        val email = emailState.value
        val password = passwordState.value

        validate()

        viewModelScope.launch {
            Log.d("SignInViewModel", "Email: $email, Password: $password")
        }
    }

    fun signInWithGoogle(result: ActivityResult, navigateForward: () -> Unit) {
        if (result.resultCode == Activity.RESULT_OK) {
            val intent = result.data
            if (intent != null) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(intent)
                try {
                    val account = task.getResult(ApiException::class.java)
                    val credential = GoogleAuthProvider.getCredential(account?.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                CoroutineScope(Dispatchers.Main).launch {
                                    navigateForward()
                                }

                            } else {
                                Log.w(
                                    "LOGIN_RESULT",
                                    "signInWithCredential:failure",
                                    task.exception
                                )
                            }
                        }
                } catch (e: ApiException) {
                    Log.w(TAG, "Google sign in failed", e)
                }
            }
        }
    }

    @Composable
    fun getGoogleSignInClient(activity: Activity): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestIdToken(stringResource(R.string.web_client_id))
            .requestId()
            .requestProfile()
            .build()
        return GoogleSignIn.getClient(activity, gso)
    }

}