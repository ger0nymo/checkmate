package com.geronymo.checkmate.data.models

import com.geronymo.checkmate.utils.enums.TodoPrivacyLevelEnum

data class Todo(
    val title: String,
    val description: String,
    val uid: String,
    val photoUrl: String? = null,
    val likes: Int = 0,
    val comments: List<Comment> = emptyList(),
    val privacyLevel: TodoPrivacyLevelEnum = TodoPrivacyLevelEnum.PUBLIC
)
