package com.example.recipeandroidapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.recipeandroidapp.domain.model.FilterParams
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.usecases.RecipeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val recipeUseCases: RecipeUseCases
): ViewModel() {

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
}