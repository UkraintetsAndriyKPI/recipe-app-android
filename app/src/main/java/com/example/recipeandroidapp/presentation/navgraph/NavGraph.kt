package com.example.recipeandroidapp.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.recipeandroidapp.presentation.navigator.Navigator
import com.example.recipeandroidapp.presentation.onboarding.OnBoardingScreen
import com.example.recipeandroidapp.presentation.onboarding.OnBoardingViewModel

@Composable
fun NavGraph(
    startDestination: String
){
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination){
        navigation(
            startDestination = Route.OnBoardingScreen.route,
            route = Route.AppStartNavigation.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(event = viewModel::onEvent)
            }
        }

        navigation(
            startDestination = Route.RecipeNavigatorScreen.route,
            route = Route.RecipeNavigation.route,
        ){
            composable(
                route = Route.RecipeNavigatorScreen.route
            ) {
                Navigator()
            }
        }
    }
}