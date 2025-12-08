package com.example.recipeandroidapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipeandroidapp.presentation.navgraph.Route
import com.example.recipeandroidapp.ui.common.RecipeCardsList
import com.example.recipeandroidapp.ui.common.SearchBar
import com.example.recipeandroidapp.util.Dimens.MediumPadding1

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    event: (SearchEvent) -> Unit,
    navigate: (String) -> Unit
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

        state.recipes?.let {
            val recipes = it.collectAsLazyPagingItems()
            RecipeCardsList(recipes = recipes, onClick = {navigate(Route.DetailsScreen.route)})
        }

    }


}