package com.geronymo.checkmate.ui.screens.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geronymo.checkmate.utils.InputValidator
import com.geronymo.checkmate.utils.ValidationResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {
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

    fun signIn() {
        val email = emailState.value
        val password = passwordState.value

        validate()

        viewModelScope.launch {
            Log.d("SignInViewModel", "Email: $email, Password: $password")
        }
    }
}