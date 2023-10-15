package com.geronymo.checkmate.data.models

import com.google.firebase.Timestamp

data class Comment(
    val authorUid: String,
    val comment: String,
    val date: Timestamp
)