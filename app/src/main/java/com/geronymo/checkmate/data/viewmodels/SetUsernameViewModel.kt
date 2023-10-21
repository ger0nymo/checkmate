package com.geronymo.checkmate.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.geronymo.checkmate.data.repositories.UserRepository
import com.geronymo.checkmate.utils.InputValidator
import com.geronymo.checkmate.utils.ValidationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.flow.MutableStateFlow

class SetUsernameViewModel : ViewModel() {
    private val _usernameState = MutableStateFlow("")
    private val _success = MutableStateFlow(false)
    private val _usernameValidationState = MutableStateFlow(ValidationResult(true, ""))
    private val _buttonLoading = MutableStateFlow(false)

    val usernameState = _usernameState
    val usernameValidationState = _usernameValidationState
    val success = _success
    val buttonLoading = _buttonLoading

    private val currentUid = FirebaseAuth.getInstance().currentUser?.uid!!

    fun setUsername(username: String) {
        _usernameState.value = username
        validate()
    }

    private fun validate() {
        _usernameValidationState.value = InputValidator.validateUsername(usernameState.value)
    }

    fun setUsernameInDatabase() {
        _buttonLoading.value = true
        UserRepository.getUsernameAvailability(usernameState.value).addOnSuccessListener {
            if (it) {
                UserRepository.updateUsername(currentUid, usernameState.value).addOnSuccessListener {
                    _success.value = true
                }
            } else {
                _buttonLoading.value = false
                _usernameValidationState.value = ValidationResult(false, "Username already exists")
            }
        }.addOnFailureListener() {
            _buttonLoading.value = false
            Log.e("SetUsernameViewModel", "Error checking username availability in database", it)
        }

    }
}