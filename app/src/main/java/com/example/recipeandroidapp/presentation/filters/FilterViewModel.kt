package com.example.recipeandroidapp.presentation.filters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
open class FilterViewModel @Inject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FilterState())
    open val state: StateFlow<FilterState> = _state

    init {
        loadFilters()
    }

    private fun loadFilters() {
            viewModelScope.launch {
                try {
                    val categories = repository.getAllCategories()
                    val tags = repository.getAllTags()

                    _state.value = _state.value.copy(
                        allCategories = categories,
                        allTags = tags
                    )
                } catch (e: Exception) {
                    println("Error loading filters: ${e.message}")
                }
        }
    }

    fun setSelectedTags(tags: Set<Int>) {
        _state.value = _state.value.copy(selectedTags = tags)
    }

    fun setSelectedCategories(categories: Set<Int>) {
        _state.value = _state.value.copy(selectedCategories = categories)
    }

    fun onEvent(event: FilterEvent) {
        when (event) {
            is FilterEvent.ToggleCategory -> {
                val newSet = state.value.selectedCategories.toMutableSet()
                if (!newSet.add(event.categoryId)) newSet.remove(event.categoryId)
                _state.value = state.value.copy(selectedCategories = newSet)
            }

            is FilterEvent.ToggleTag -> {
                val newSet = state.value.selectedTags.toMutableSet()
                if (!newSet.add(event.tagId)) newSet.remove(event.tagId)
                _state.value = state.value.copy(selectedTags = newSet)
            }

            FilterEvent.ApplyFilters -> {
                // TODO: send to search screen
            }
        }
    }
}