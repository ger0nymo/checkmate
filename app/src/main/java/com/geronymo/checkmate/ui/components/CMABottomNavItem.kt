package com.geronymo.checkmate.ui.components

import android.graphics.drawable.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.geronymo.checkmate.R
import com.geronymo.checkmate.utils.enums.ScreenNameEnum

sealed class BottomNavItem(
    var title: String,
    var selectedIcon: Int,
    var unselectedIcon: Int,
    var screen: ScreenNameEnum
) {
    object Feeds :
        BottomNavItem(
            "Feeds",
            R.drawable.baseline_feed_24,
            R.drawable.outline_feed_24,
            ScreenNameEnum.FEEDS
        )
    object Add :
        BottomNavItem(
            "Search",
            R.drawable.baseline_search_24,
            R.drawable.baseline_search_24,
            ScreenNameEnum.SEARCH
        )
    object Profile :
        BottomNavItem(
            "Profile",
            R.drawable.baseline_person_24,
            R.drawable.baseline_person_outline_24,
            ScreenNameEnum.PROFILE
        )
}