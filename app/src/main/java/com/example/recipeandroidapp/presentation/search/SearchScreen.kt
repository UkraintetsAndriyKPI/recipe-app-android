package com.example.recipeandroidapp.presentation.search

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.ui.common.RecipeCardsList
import com.example.recipeandroidapp.ui.common.SearchBar
import com.example.recipeandroidapp.util.Dimens.MediumPadding1

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigateToDetails: (Recipe) -> Unit,
    recipes: LazyPagingItems<Recipe>
) {
    Column(
        modifier = Modifier
            .padding(
                top = MediumPadding1, start = MediumPadding1, end = MediumPadding1
            )
            .statusBarsPadding()
            .fillMaxSize()
    ) {
        SearchBar(

            searchText = state.searchQuery,
            readOnly = false,
            onTextChange = { event(SearchEvent.UpdateSearchQuery(it)) },
            onFilterClick = { /*TODO()*/ },
            onSearch = { event(SearchEvent.SearchRecipes) })

        Spacer(
            modifier = Modifier.height(MediumPadding1)
        )


        RecipeCardsList(
            recipes = recipes,
            onClick = { recipe -> navigateToDetails(recipe) },
        )

    }


}