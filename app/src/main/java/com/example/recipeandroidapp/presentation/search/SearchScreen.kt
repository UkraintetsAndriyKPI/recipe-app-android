package com.example.recipeandroidapp.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.presentation.filters.SelectedFiltersRow
import com.example.recipeandroidapp.ui.common.RecipeCardsList
import com.example.recipeandroidapp.ui.common.SearchBar
import com.example.recipeandroidapp.util.Dimens.MediumPadding1

@Composable
fun SearchScreen(
    viewModel: SearchViewModel,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Recipe) -> Unit,
    navigateToFilter: () -> Unit,
    modifier: Modifier = Modifier
) {
    val state = viewModel.state.value
    val recipes = viewModel.recipes.collectAsLazyPagingItems()

    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1, start = MediumPadding1, end = MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {

        SearchBar(
            searchText = state.searchQuery.toString(),
            readOnly = false,

            onValueChange = { value ->
                Log.d("SearchScreen searchBar event", "onValueChange: $value")
                event(SearchEvent.UpdateSearchQuery(value))
            },
            onFilterClick = { navigateToFilter() },
            onSearch = { event(SearchEvent.SearchRecipes) }
        )

        SelectedFiltersRow(
            selectedCategories = state.selectedCategories,
            selectedTags = state.selectedTags,
            allCategories = state.allCategories,
            allTags = state.allTags,
        )


        RecipeCardsList(
            recipes = recipes,
            onClick = { recipe -> navigateToDetails(recipe) },
        )
    }
}
