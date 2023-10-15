package com.geronymo.checkmate.data.viewmodels

import androidx.lifecycle.ViewModel
import com.geronymo.checkmate.data.models.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class UserViewModel() : ViewModel() {
    private val _userState = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _userState

    init {
        getUser()
    }

    fun getUser() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser
        if (firebaseUser != null) {
            val user = User(
                uid = firebaseUser.uid,
                email = firebaseUser.email ?: "",
                username = firebaseUser.displayName ?: "",
                profilePictureUrl = firebaseUser.photoUrl.toString()
            )
            _userState.value = user
        }
    }
}