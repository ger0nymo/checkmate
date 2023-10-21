package com.geronymo.checkmate.data.models

data class User(
    var uid: String? = "",
    var email: String? = "",
    var username: String? = "",
    var profilePictureUrl: String? = "",
    var todos: List<Post>? = emptyList(),
    var followers: List<User>? = emptyList(),
    var following: List<User>? = emptyList()
) { }