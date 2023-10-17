package com.geronymo.checkmate.data.models

data class User(
    val uid: String,
    val email: String,
    val username: String,
    val profilePictureUrl: String = "",
    val todos: List<Post> = emptyList(),
    val followers: List<User> = emptyList(),
    val following: List<User> = emptyList()
) {
    fun getFollowedFollowers(): List<User> {
        return followers.filter { it in following }
    }
}