package com.example.recipeandroidapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeandroidapp.domain.usecases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.emptyList

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(DailyRecipesState())
    val state: StateFlow<DailyRecipesState> = _state

    fun loadDailyRecipes() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                recipeUseCases.getDailyRecipes().collect { list ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        recipes = list,
                        error = null
                    )
                }

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    recipes = emptyList(),
                    error = e.message ?: "Unexpected error"
                )
            }
        }
    }
}