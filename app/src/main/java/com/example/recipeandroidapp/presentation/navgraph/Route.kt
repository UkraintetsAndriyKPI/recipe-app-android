package com.example.recipeandroidapp.presentation.navgraph

sealed class Route(
    val route: String
) {
    object OnBoardingScreen: Route(route = "onBoardingScreen")
    object HomeScreen: Route(route = "homeScreen")
    object SearchScreen: Route(route = "searchScreen")
    object BookmarksScreen: Route(route = "bookmarksScreen")
    object DetailsScreen: Route(route = "detailsScreen")
    object AppStartNavigation: Route(route = "appStartNavigation")
    object RecipeNavigation: Route(route = "recipeNavigation")
    object RecipeNavigatorScreen: Route(route = "recipeNavigatorScreen")
}