package com.example.recipeandroidapp.presentation.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailTopBar(
    modifier: Modifier = Modifier,
    onBookmarkClick: () -> Unit,
    onBackClick: () -> Unit,
    isBookmarked: Boolean
) {
    TopAppBar(
        title = {
            Text(
                text = "Recipe details",
                style = MaterialTheme.typography.titleLarge
            )
        },
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent,
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onBookmarkClick) {
                Icon(
                    imageVector = if (isBookmarked)
                        Icons.Filled.Bookmark
                    else
                        Icons.Outlined.BookmarkBorder,
                    contentDescription = "Bookmark",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )

}


@Preview(showBackground = true)
@Composable
fun DetailTopBarPreview() {
    MaterialTheme {
        DetailTopBar(
            onBackClick = {},
            onBookmarkClick = {},
            isBookmarked = false
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DetailTopBarPreview2() {
    MaterialTheme {
        DetailTopBar(
            onBackClick = {},
            onBookmarkClick = {},
            isBookmarked = true
        )
    }
}