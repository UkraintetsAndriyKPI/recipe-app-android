package com.example.recipeandroidapp.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.util.Dimens.SmallPadding1

@Composable
fun RecipeCardsList(
    modifier: Modifier = Modifier,
    recipes: List<Recipe>,
    onClick: (Recipe) -> Unit
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(count = recipes.size) { index ->
            RecipeCard(recipe = recipes[index], onClick = { onClick(recipes[index]) })
        }
    }
}

@Composable
fun handleListResult(
    recipes: List<Recipe>
): Boolean {
    if (recipes.isEmpty()) {
        EmptyScreen(error = null as String?)
        return false
    }
    return true
}


@Composable
fun RecipeCardsList(
    modifier: Modifier = Modifier,
    recipes: LazyPagingItems<Recipe>,
    onClick: (Recipe) -> Unit
) {
    val handlePagingResult = handlePagingResult(recipes = recipes)
    if (handlePagingResult){
        LazyColumn(modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(8.dp)
        ) {
            items(count = recipes.itemCount){
                recipes[it]?.let { recipe ->
                    RecipeCard(recipe = recipe, onClick = { onClick(recipe) })
                }

            }
        }
    }

}

@Composable
fun handlePagingResult(
    recipes: LazyPagingItems<Recipe>,
): Boolean {
    val loadState = recipes.loadState
    val error = when {
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when {
        loadState.refresh is LoadState.Loading -> {
            ShimmerEffect()
            false
        }
        error != null ->{
            EmptyScreen(error = error)
            false
        }
        else -> true

    }
}

@Composable
private fun ShimmerEffect(){
    Column(verticalArrangement = Arrangement.spacedBy(SmallPadding1)) {
        repeat(5){
            RecipeCardShimmerEffect()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ShimmerEffectPreview() {
    MaterialTheme {
        ShimmerEffect()
    }
}