package com.example.recipeandroidapp.presentation.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.presentation.navgraph.Route
import com.example.recipeandroidapp.ui.common.RecipeCardsList
import com.example.recipeandroidapp.ui.common.SearchBar

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    recipes: LazyPagingItems<Recipe>,
    navigation: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    ) {

        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            searchText = "Tap to search",
            readOnly = true,
            onTextChange = { },
            onFilterClick = { },
            onSearchClick = {
                navigation(Route.SearchScreen.route)
            },
            onSearch = {
                // TODO: perform search
            })

        RecipeCardsList(
            recipes = recipes, onClick = {
                navigation(Route.DetailsScreen.route)
            }
        )
    }
}