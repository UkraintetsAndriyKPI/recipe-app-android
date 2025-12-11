package com.example.recipeandroidapp.presentation.navigator

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    items: List<BottomNavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = modifier
            .fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEachIndexed { index, item ->
            val iconTint by animateColorAsState(
                targetValue = if (index == selectedItem) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )

            val labelTint by animateColorAsState(
                targetValue = if (index == selectedItem) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )

            val interactionSource = remember { MutableInteractionSource() }

            NavigationBarItem(
                selected = index == selectedItem,
                onClick = { onItemClick(index) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    indicatorColor = Color.Transparent
                ),
                icon = {
                    if (item.badgeCount > 0) {
                        BadgedBox(
                            badge = {
                                Badge { Text(item.badgeCount.toString(), color = Color.White) }
                            }
                        ) {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label,
                                tint = iconTint
                            )
                        }
                    } else {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = iconTint
                        )
                    }
                },
                label = { Text(item.label, color = labelTint) },
                interactionSource = interactionSource
            )
        }
    }
}

data class BottomNavigationItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val badgeCount: Int = 0
)


@Preview(showBackground = true)
@Composable
fun BottomNavigationBarPreviewStatic() {
    var selectedIndex by remember { mutableIntStateOf(0) }

    val bottomNavItems = listOf(
        BottomNavigationItem("home", Icons.Filled.Home, "Home"),
        BottomNavigationItem("bookmarks", Icons.Filled.Bookmark, "Bookmarks", badgeCount = 3),
        BottomNavigationItem("profile", Icons.Filled.Person, "Profile")
    )

    BottomNavigationBar(
        items = bottomNavItems,
        selectedItem = selectedIndex,
        onItemClick = { selectedIndex = it }
    )
}