package com.example.recipeandroidapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.ui.common.EmptyScreen
import com.example.recipeandroidapp.ui.common.RecipeCardShimmerEffect
import com.example.recipeandroidapp.ui.common.RecipeCardsList
import com.example.recipeandroidapp.ui.common.SearchBar
import com.example.recipeandroidapp.util.Dimens.MediumPadding1
import com.example.recipeandroidapp.util.Dimens.SmallPadding1

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    state: DailyRecipesState,
    navigateToSearch: () -> Unit,
    navigateToDetails: (Recipe) -> Unit,
    navigateToFilter: () -> Unit,
) {
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1,
                start = MediumPadding1,
                end = MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        SearchBar(
            searchText = "Tap to search",
            readOnly = true,
            onValueChange = { },
            onFilterClick = navigateToFilter,
            onSearchClick = navigateToSearch,
            onSearch = {}
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

        Text(
            text = "Daily Recipes",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = SmallPadding1)
        )

        when {
            state.isLoading -> {
                Column(verticalArrangement = Arrangement.spacedBy(SmallPadding1)) {
                    repeat(3){
                        RecipeCardShimmerEffect()
                    }
                }
            }

            state.error != null -> {
                EmptyScreen(
                    error = state.error
                )
            }

            else -> {
                RecipeCardsList(
                    recipes = state.recipes,
                    onClick = { navigateToDetails(it) }
                )
            }
        }
    }
}