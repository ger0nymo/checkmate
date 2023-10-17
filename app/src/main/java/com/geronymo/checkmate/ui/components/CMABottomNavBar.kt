package com.geronymo.checkmate.ui.components

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun BottomNavigation(currentScreen: String) {
    var currentSelected: String by remember { mutableStateOf(currentScreen) }

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Add,
        BottomNavItem.Profile
    )

    BottomAppBar(
        modifier = Modifier.navigationBarsPadding()
    ) {
        items.forEach { item ->
            AddItem(
                screen = item,
                selectScreen = {
                    currentSelected = item.title
                },
                selectedScreen = currentSelected
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomNavItem,
    selectedScreen: String,
    selectScreen: () -> Unit
) {
    NavigationBarItem(

        // Text that shows bellow the icon
        label = {
            Text(text = screen.title, color = if (isSystemInDarkTheme() && screen.title == selectedScreen) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface)
        },

        // The icon resource
        icon = {
            Icon(
                painter = painterResource(
                    id = if (screen.title == selectedScreen) screen.selectedIcon else screen.unselectedIcon
                ),
                contentDescription = screen.title
            )
        },

        // Display if the icon it is select or not
        selected = screen.title == selectedScreen,

        // Always show the label bellow the icon or not
        alwaysShowLabel = true,

        // Click listener for the icon
        onClick = selectScreen,

        // Control all the colors of the icon
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
            selectedIconColor = MaterialTheme.colorScheme.onPrimaryContainer,

        ),
    )
}