package com.geronymo.checkmate.ui.components

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.geronymo.checkmate.R

sealed class BottomNavItem(
    var title: String,
    var selectedIcon: Int,
    var unselectedIcon: Int
) {
    object Home :
        BottomNavItem(
            "Home",
            R.drawable.round_home_24,
            R.drawable.outline_home_24
        )
    object Add :
        BottomNavItem(
            "New todo",
            R.drawable.round_add_box_24,
            R.drawable.outline_add_box_24
        )
    object Profile :
        BottomNavItem(
            "Profile",
            R.drawable.baseline_person_24,
            R.drawable.baseline_person_outline_24
        )
}