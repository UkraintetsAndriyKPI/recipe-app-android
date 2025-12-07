package com.example.recipeandroidapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeandroidapp.domain.usecases.AppEntryUseCases
import com.example.recipeandroidapp.presentation.navgraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
): ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set
    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        appEntryUseCases.readAppEntry().onEach { shouldStartHomeScreen ->
            if (shouldStartHomeScreen){
                startDestination = Route.RecipeNavigation.route
            } else {
                startDestination = Route.AppStartNavigation.route
                delay(200)
            }
            splashCondition = false
        }.launchIn(viewModelScope)
    }
}