package com.example.recipeandroidapp.presentation.navigator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
                    navigateToFilter = { navController.navigate(Route.FilterScreen.route) }
                )
            }

            composable(route = Route.SearchScreen.route) {
                val viewModel: SearchViewModel = hiltViewModel()
                val recipes = viewModel.recipes.collectAsLazyPagingItems()
                SearchScreen(
                    state = viewModel.state.value,
                    recipes = recipes,
                    event = viewModel::onEvent,
                    navigateToDetails = { recipe -> navigateToDetails(navController, recipe) },
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
//                val viewModel: FilterViewModel = hiltViewModel()
//
//                FilterScreen(
//                    state = viewModel.state.value,
//                    event = viewModel::onEvent,
//                    navigateUp = { navController.navigateUp() }
//                )
                Text("TODO FILTER SCREEN")
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