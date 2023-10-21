package com.geronymo.checkmate.data.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.geronymo.checkmate.data.models.User
import com.geronymo.checkmate.data.repositories.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserViewModel() : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    suspend fun getUser(): Boolean {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val user = UserRepository.getUserFromDatabase(firebaseUser.uid)
            _user.value = user
            return user != null
        }
        return false
    }

}