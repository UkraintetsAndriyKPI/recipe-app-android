package com.example.recipeandroidapp.presentation.bookmark

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.ui.common.RecipeCardsList
import com.example.recipeandroidapp.util.Dimens.MediumPadding1

@Composable
fun BookmarkScreen(
    modifier: Modifier = Modifier,
    state: BookmarkState,
    navigateToDetails: (Recipe) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(MediumPadding1)
    ) {

        // Screen title
        Text(
            text = "Bookmarks",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Empty state
        if (state.recipes.isEmpty()) {
            EmptyBookmarks()
        } else {
            // Recipes list
            RecipeCardsList(
                recipes = state.recipes,
                onClick = { recipe ->
                    navigateToDetails(recipe)
                }
            )
        }
    }
}

@Composable
fun EmptyBookmarks() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 60.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            imageVector = Icons.Outlined.BookmarkBorder,
            contentDescription = "Empty bookmarks",
            modifier = Modifier.size(120.dp),
            tint = MaterialTheme.colorScheme.secondaryContainer
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "You don’t have any bookmarks yet",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "Save recipes to see them here.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
    }
}


@Preview(showBackground = true)
@Composable
fun BookmarkScreenEmptyPreview() {
    val fakeState = BookmarkState(
        recipes = emptyList()
    )

    BookmarkScreen(
        state = fakeState,
        navigateToDetails = {}
    )
}

//@Preview(showBackground = true)
//@Composable
//fun BookmarkScreenWithDataPreview() {
//    val fakeState = BookmarkState(
//        recipes = listOf(
//            Recipe(
//                title = "Pasta Carbonara",
//                description = "Classic Italian pasta with creamy sauce.",
//                cooking_time_min = 20,
//                creation_date = "2024-12-01",
//                is_published = true,
//                image = null,
//                categories = emptyList(),
//                tags = emptyList()
//            )
//        )
//    )
//
//    BookmarkScreen(
//        state = fakeState,
//        navigateToDetails = {}
//    )
//}