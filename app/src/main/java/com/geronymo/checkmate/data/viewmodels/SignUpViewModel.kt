package com.geronymo.checkmate.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.geronymo.checkmate.data.models.User
import com.geronymo.checkmate.data.repositories.UserRepository
import com.geronymo.checkmate.utils.InputValidator
import com.geronymo.checkmate.utils.ValidationResult
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel : ViewModel() {
    private val _emailState = MutableStateFlow("")
    private val _usernameState = MutableStateFlow("")
    private val _passwordState = MutableStateFlow("")
    private val _confirmPasswordState = MutableStateFlow("")
    private val _emailValidationState = MutableStateFlow(ValidationResult(true, ""))
    private val _usernameValidationState = MutableStateFlow(ValidationResult(true, ""))
    private val _passwordValidationState = MutableStateFlow(ValidationResult(true, ""))
    private val _confirmPasswordValidationState = MutableStateFlow(ValidationResult(true, ""))

    val emailState: StateFlow<String> = _emailState
    val usernameState: StateFlow<String> = _usernameState
    val passwordState: StateFlow<String> = _passwordState
    val confirmPasswordState: StateFlow<String> = _confirmPasswordState
    val emailValidationState: StateFlow<ValidationResult> = _emailValidationState
    val usernameValidationState: StateFlow<ValidationResult> = _usernameValidationState
    val passwordValidationState: StateFlow<ValidationResult> = _passwordValidationState
    val confirmPasswordValidationState: StateFlow<ValidationResult> =
        _confirmPasswordValidationState

    fun setEmail(email: String) {
        _emailState.value = email
    }

    fun setUsername(username: String) {
        _usernameState.value = username
    }

    fun setPassword(password: String) {
        _passwordState.value = password
    }

    fun setConfirmPassword(confirmPassword: String) {
        _confirmPasswordState.value = confirmPassword
    }

    fun validate() {
        _emailValidationState.value = InputValidator.validateEmail(emailState.value)
        _usernameValidationState.value = InputValidator.validateUsername(usernameState.value)
        _passwordValidationState.value = InputValidator.validatePassword(passwordState.value)
        _confirmPasswordValidationState.value =
            InputValidator.validateConfirmPassword(passwordState.value, confirmPasswordState.value)
        if (!passwordValidationState.value.isValid) {
            _confirmPasswordValidationState.value =
                ValidationResult(false, "Password must be valid")
        }
    }

    fun signUp(navController: NavController) {
        val email = emailState.value
        val password = passwordState.value
        val username = usernameState.value
        val confirmPassword = confirmPasswordState.value

        validate()

        val canSignUp = emailValidationState.value.isValid &&
                usernameValidationState.value.isValid &&
                passwordValidationState.value.isValid &&
                confirmPasswordValidationState.value.isValid


        viewModelScope.launch {
            if (canSignUp) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener() { task ->
                        if (task.isSuccessful) {
                            Log.d("SignUpViewModel", "Email: $email, Password: $password")
                            val user = FirebaseAuth.getInstance().currentUser
                            user?.sendEmailVerification()
                                ?.addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        val databaseUser = User(
                                            uid = user.uid,
                                            email = email,
                                            username = username,
                                        )
                                        UserRepository.signUpUserIntoDatabase(databaseUser)
                                        navController.navigate("VerifyEmail") {
                                            popUpTo(navController.graph.id) { inclusive = true }
                                        }
                                    }
                                }
                        } else {
                            Log.w("SignUpViewModel", "createUserWithEmail:failure", task.exception)
                        }
                    }
                Log.d(
                    "SignUpViewModel",
                    "Email: $email, Username: $username, Password: $password, Confirm Password: $confirmPassword"
                )
            }
        }
    }
}