package com.geronymo.checkmate.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.geronymo.checkmate.data.models.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

object UserRepository {
    private val db = Firebase.firestore
    fun signUpUserIntoDatabase(user: User) {
        db.collection("users").document(user.uid).set(user)
            .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
            .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
    }
}