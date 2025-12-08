package com.example.recipeandroidapp.presentation.search

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.recipeandroidapp.domain.usecases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state


    fun onEvent(event: SearchEvent){
        when(event) {
            is SearchEvent.UpdateSearchQuery -> {
                _state.value = state.value.copy(searchQuery = event.searchQuery)
            }

            is SearchEvent.ToggleCategory -> {
                val categories = state.value.selectedCategories.toMutableSet()
                if (categories.contains(event.categoryId))
                    categories.remove(event.categoryId)
                else
                    categories.add(event.categoryId)

                _state.value = state.value.copy(selectedCategories = categories)
            }

            is SearchEvent.ToggleTag -> {
                val tags = state.value.selectedTags.toMutableSet()
                if (tags.contains(event.tagId))
                    tags.remove(event.tagId)
                else
                    tags.add(event.tagId)

                _state.value = state.value.copy(selectedTags = tags)
            }


            SearchEvent.SearchRecipes -> {
                searchRecipes()
            }

            else -> {}
        }
    }

    private fun searchRecipes() {
        val recipes = recipeUseCases.getRecipes(
            tagIds = state.value.selectedTags,
            categoryIds = state.value.selectedCategories,
            search = state.value.searchQuery,
        ).cachedIn(viewModelScope)

        _state.value = state.value.copy(recipes = recipes)
    }

}