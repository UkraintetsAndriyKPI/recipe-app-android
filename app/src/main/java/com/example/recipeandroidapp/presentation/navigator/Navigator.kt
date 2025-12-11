package com.example.recipeandroidapp.presentation.navigator

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.presentation.bookmark.BookmarkScreen
import com.example.recipeandroidapp.presentation.bookmark.BookmarkViewModel
import com.example.recipeandroidapp.presentation.detail.DetailScreen
import com.example.recipeandroidapp.presentation.detail.DetailViewModel
import com.example.recipeandroidapp.presentation.filters.FilterScreen
import com.example.recipeandroidapp.presentation.filters.FilterViewModel
import com.example.recipeandroidapp.presentation.home.HomeScreen
import com.example.recipeandroidapp.presentation.home.HomeViewModel
import com.example.recipeandroidapp.presentation.navgraph.Route
import com.example.recipeandroidapp.presentation.search.SearchScreen
import com.example.recipeandroidapp.presentation.search.SearchViewModel

@Composable
fun Navigator(modifier: Modifier = Modifier) {
    val bottomNavigationItems = remember {
        listOf(
            BottomNavigationItem("home", Icons.Filled.Home, "Home"),
            BottomNavigationItem("search", Icons.Filled.Search, "Search"),
            BottomNavigationItem("bookmarks", Icons.Filled.Bookmark, "Bookmarks"),
        )
    }

    val navController = rememberNavController()
    val backStackState = navController.currentBackStackEntryAsState().value
    var selectedItem by rememberSaveable {
        mutableIntStateOf(0)
    }

    selectedItem = when (backStackState?.destination?.route) {
        Route.HomeScreen.route -> 0
        Route.SearchScreen.route -> 1
        Route.BookmarksScreen.route -> 2
        else -> 0
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavigationItems,
                selectedItem = selectedItem,
                onItemClick = { index ->
                    when (index) {
                        0 -> navigateToTab(navController, Route.HomeScreen.route)
                        1 -> navigateToTab(navController, Route.SearchScreen.route)
                        2 -> navigateToTab(navController, Route.BookmarksScreen.route)
                    }
                }
            )
        }
    ) {
        val bottomPadding = it.calculateBottomPadding()

        NavHost(
            navController = navController,
            startDestination = Route.HomeScreen.route,
            modifier = Modifier.padding(bottom = bottomPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                val viewModel: HomeViewModel = hiltViewModel()
                val state by viewModel.state.collectAsState()

                LaunchedEffect(Unit) {
                    viewModel.loadDailyRecipes()
                }

                HomeScreen(
                    state = state,
                    navigateToSearch = { navigateToTab(navController, Route.SearchScreen.route) },
                    navigateToDetails = { recipe -> navigateToDetails(navController, recipe) },
                    navigateToFilter = { navigateToTab(navController, Route.SearchScreen.route) }
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()

                val tags = navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.get<Set<Int>>("selectedTags")

                val categories = navController.currentBackStackEntry
                    ?.savedStateHandle
                    ?.get<Set<Int>>("selectedCategories")

                Log.d("NAVIGATOR SearchScreen", "SavedTags received: $tags")
                Log.d("NAVIGATOR SearchScreen", "SavedCategories received: $categories")

                LaunchedEffect(tags, categories) {
                    if ((tags != null) && (categories != null)) {

                        Log.d("NAVIGATOR SearchScreen", "Applying filters -> tags: $tags, categories: $categories")

                        viewModel.setTags(tags)
                        viewModel.setCategories(categories)

                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.remove<Set<Int>>("selectedTags")
                        navController.currentBackStackEntry
                            ?.savedStateHandle
                            ?.remove<Set<Int>>("selectedCategories")

                        Log.d("NAVIGATOR SearchScreen", "selectedTags and selectedCategories removed from SavedStateHandle")
                    } else {
                        Log.d("NAVIGATOR SearchScreen", "savedFilters == null")
                    }
                }

                SearchScreen(
                    viewModel = viewModel,
                    event = viewModel::onEvent,
                    navigateToDetails = { recipe -> navigateToDetails(navController, recipe) },
                    navigateToFilter = {
                        navigateToFilter(
                            navController = navController,
                            tags = viewModel.state.value.selectedTags,
                            categories = viewModel.state.value.selectedCategories,
                        )
                    },
                )
            }

            composable(route = Route.DetailsScreen.route) {
                val viewModel: DetailViewModel = hiltViewModel()

                // TODO(): Handle side effect
                navController.previousBackStackEntry?.savedStateHandle?.get<Recipe>("recipe")?.let { recipe ->
                    LaunchedEffect(recipe) {
                        viewModel.loadRecipe(recipe)
                    }

                    DetailScreen(
                        recipe = recipe,
                        event = viewModel::onEvent,
                        navigateUp = { navController.navigateUp() },
                        viewModel = viewModel
                    )
                }
            }

            composable(route = Route.BookmarksScreen.route){
                val viewModel: BookmarkViewModel = hiltViewModel()
                BookmarkScreen(
                    state = viewModel.state.value,
                    navigateToDetails = { recipe -> navigateToDetails(navController, recipe) }
                )
            }

            composable(route = Route.FilterScreen.route) {
                val viewModel: FilterViewModel = hiltViewModel()

                FilterScreen(
                    viewModel = viewModel,
                    event = viewModel::onEvent,
                    navigateUp = { navController.navigateUp() },
                    onApply = { selectedCategories, selectedTags ->
                        applyFiltersAndNavigateUp(
                            navController = navController,
                            selectedCategories = selectedCategories,
                            selectedTags = selectedTags
                        )
                    }
                )
            }
        }

    }
}

private fun navigateToTab(navController: NavController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                inclusive = true
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }

}

private fun navigateToDetails(navController: NavController, recipe: Recipe) {
    navController.currentBackStackEntry?.savedStateHandle?.set("recipe", recipe)
    navController.navigate(route = Route.DetailsScreen.route)
}

private fun navigateToFilter(
    navController: NavController,
    tags: Set<Int>,
    categories: Set<Int>
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("selectedTags", tags)
    navController.currentBackStackEntry?.savedStateHandle?.set("selectedCategories", categories)

    navController.navigate(Route.FilterScreen.route) {
        navController.graph.startDestinationRoute?.let { homeScreen ->
            popUpTo(homeScreen) {
                inclusive = true
                saveState = true
            }
            restoreState = true
            launchSingleTop = true
        }
    }
}

private fun applyFiltersAndNavigateUp(
    navController: NavController,
    selectedCategories: Set<Int>,
    selectedTags: Set<Int>
) {
    Log.d("Filters", "Applying filters:")
    Log.d("Filters", "Selected Tags: $selectedTags")
    Log.d("Filters", "Selected Categories: $selectedCategories")

    navController.previousBackStackEntry?.savedStateHandle?.set("selectedTags", selectedTags)
    navController.previousBackStackEntry?.savedStateHandle?.set("selectedCategories", selectedCategories)

    Log.d("Filters", "Filters saved. Navigating up...")
    navController.navigateUp()
}