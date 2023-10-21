package com.geronymo.checkmate.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextMotion.Companion.Animated

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CMATopBar(title: String) {
    CenterAlignedTopAppBar(
        title = {
            AnimatedVisibility(
                visible = title == "Feeds",
                enter = fadeIn(animationSpec = tween(50, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(50, easing = LinearEasing))) {
                Text(text = title, fontWeight = FontWeight.Bold)
            }
            AnimatedVisibility(visible = title == "Search",
                enter = fadeIn(animationSpec = tween(50, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(50, easing = LinearEasing))) {
                Text(text = title, fontWeight = FontWeight.Bold)
            }
            AnimatedVisibility(visible = title == "Profile",
                enter = fadeIn(animationSpec = tween(50, easing = LinearEasing)),
                exit = fadeOut(animationSpec = tween(50, easing = LinearEasing))) {
                Text(text = title, fontWeight = FontWeight.Bold)
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceTint
        ),
    )
}