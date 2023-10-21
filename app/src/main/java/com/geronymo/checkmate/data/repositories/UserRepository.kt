package com.geronymo.checkmate.data.repositories

import android.content.ContentValues.TAG
import android.util.Log
import com.geronymo.checkmate.data.models.User
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object UserRepository {
    fun signUpUserIntoDatabase(user: User) {
        val db = Firebase.firestore
        user.uid?.let {
            db.collection("users").document(it).set(user)
                .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
                .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }
        }
    }
    suspend fun getUserFromDatabase(uid: String) : User? {
        val db = Firebase.firestore
        return try {
            val documentSnapshot = db.collection("users").document(uid).get().await()

            if (documentSnapshot.exists()) {
                Log.d(TAG, "DocumentSnapshot data: ${documentSnapshot.data}")
                val user = documentSnapshot.toObject(User::class.java)
                user
            } else {
                null
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user from database", e)
            null
        }
    }
    fun getUsernameAvailability(username: String) : Task<Boolean> {
        val db = Firebase.firestore
        return db.collection("users").whereEqualTo("username", username).get().continueWith {
            if (it.isSuccessful) {
                it.result?.isEmpty ?: true
            } else {
                throw it.exception!!
            }
        }
    }
    fun updateUsername(uid: String, username: String) : Task<Void> {
        val db = Firebase.firestore

        return db.collection("users").document(uid).update("username", username)
    }
}