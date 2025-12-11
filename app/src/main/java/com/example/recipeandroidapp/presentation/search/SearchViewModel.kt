package com.example.recipeandroidapp.presentation.search

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipeandroidapp.domain.model.FilterParams
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.usecases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {
    private val _state = mutableStateOf(SearchState())
    val state: State<SearchState> = _state

    private val _tagIds = MutableStateFlow<Set<Int>>(emptySet())
    private val _categoryIds = MutableStateFlow<Set<Int>>(emptySet())
    private val _minTime = MutableStateFlow<Int?>(null)
    private val _maxTime = MutableStateFlow<Int?>(null)
    private val _searchQuery = MutableStateFlow<String?>(null)

    @OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    val recipes: StateFlow<PagingData<Recipe>> = combine(
        _tagIds,
        _categoryIds,
        _minTime,
        _maxTime,
        _searchQuery
    ) { tags, categories, min, max, query ->
        FilterParams(tags, categories, min, max, query)
    }
        .flatMapLatest { params ->
            recipeUseCases.getRecipes(
                tagIds = params.tagIds,
                categoryIds = params.categoryIds,
                minTime = params.minTime,
                maxTime = params.maxTime,
                search = params.search
            )
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    fun setTags(tags: Set<Int>) {
        _tagIds.value = tags
    }

    fun setCategories(categories: Set<Int>) {
        _categoryIds.value = categories
    }

    fun setTimeRange(min: Int?, max: Int?) {
        _minTime.value = min
        _maxTime.value = max
    }

    fun setSearch(query: String?) {
        _searchQuery.value = query
    }


    init {
        loadFilters()
    }

    private fun loadFilters() {
        viewModelScope.launch {
            _state.value = state.value.copy(isLoading = true, error = null)
            Log.d("SearchViewModel", "Loading filters started")

            try {
                val categories = recipeUseCases.getAllCategories()
                val tags = recipeUseCases.getAllTags()

                _state.value = state.value.copy(
                    allCategories = categories.toSet(),
                    allTags = tags.toSet(),
                    isLoading = false
                )
                Log.d("SearchViewModel", "Filters loaded successfully: ${categories.size} categories, ${tags.size} tags")
            } catch (e: Exception) {
                _state.value = state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Unknown error occurred"
                )
                Log.e("SearchViewModel", "Error loading filters", e)
            }
        }
    }

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


//            SearchEvent.SearchRecipes -> {
//                searchRecipes()
//            }

            else -> {}
        }
    }

//    private fun searchRecipes() {
//        val recipesFlow = recipeUseCases.getRecipes(
//            tagIds = state.value.selectedTags,
//            categoryIds = state.value.selectedCategories,
//            search = state.value.searchQuery,
//        ).cachedIn(viewModelScope)
//
//        _state.value = state.value.copy(recipes = recipesFlow)
//    }

}