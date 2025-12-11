package com.example.recipeandroidapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            onTextChange = { },
            onFilterClick = navigateToFilter,
            onSearchClick = navigateToSearch,
            onSearch = {}
        )

        Spacer(modifier = Modifier.height(MediumPadding1))

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