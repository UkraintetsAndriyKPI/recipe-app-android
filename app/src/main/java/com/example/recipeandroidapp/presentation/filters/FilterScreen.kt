package com.example.recipeandroidapp.presentation.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Tag
import com.example.recipeandroidapp.domain.repository.RecipeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

//@Composable
//fun FilterScreen(
//    modifier: Modifier = Modifier,
//    viewModel: FilterViewModel,
//    onApply: (selectedCategories: Set<Int>, selectedTags: Set<Int>) -> Unit,
//    navigateUp: () -> Boolean,
//    event: (FilterEvent) -> Unit,
//) {
//    val state by viewModel.state.collectAsState()
//
//    Column(
//        modifier = modifier
//            .padding(16.dp)
//            .statusBarsPadding()
//            .verticalScroll(rememberScrollState())
//            .fillMaxSize()
//    ) {
//
//        Text("Categories", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.height(8.dp))
//
//        state.allCategories.forEach { category ->
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Checkbox(
//                    modifier = Modifier
//                        .size(36.dp)
//                        .padding(4.dp)
//                        .background(
//                            if (category.id in state.selectedCategories) {
//                                MaterialTheme.colorScheme.primary.copy(alpha = 0.4f)
//                            } else {
//                                MaterialTheme.colorScheme.primary.copy(alpha = 0.25f)
//                            },
//                            RoundedCornerShape(8.dp)
//                        ),
//                    checked = category.id in state.selectedCategories,
//                    onCheckedChange = {
//                        event(FilterEvent.ToggleCategory(category.id))
//                    }
//                )
//                Text(
//                    text = category.category_name,
//                    modifier = Modifier.padding(start = 8.dp),
//                    fontWeight = if (category.id in state.selectedCategories) {
//                        FontWeight.Bold
//                    } else {
//                        FontWeight.Normal
//                    },
//                    color = MaterialTheme.colorScheme.primary,
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("Tags", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.height(8.dp))
//
//        state.allTags.forEach { tag ->
//            Row(
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                Checkbox(
//                    modifier = Modifier
//                        .size(36.dp)
//                        .padding(4.dp)
//                        .background(
//                            if (tag.id in state.selectedTags)
//                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.4f)
//                            else
//                                MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f),
//                            RoundedCornerShape(8.dp)
//                        ),
//                    checked = tag.id in state.selectedTags,
//                    onCheckedChange = {
//                        event(FilterEvent.ToggleTag(tag.id))
//                    }
//                )
//                Text(
//                    text = tag.tag_name,
//                    color = MaterialTheme.colorScheme.secondary,
//                    fontWeight = if (tag.id in state.selectedTags) {
//                        FontWeight.Bold
//                    } else {
//                        FontWeight.Normal
//                    },
//                    modifier = Modifier.padding(start = 8.dp)
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(24.dp))
//        Button(
//            onClick = { onApply(state.selectedCategories,
//                state.selectedTags) },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Apply Filters")
//        }
//    }
//}


@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel,
    onApply: (Set<Int>, Set<Int>) -> Unit,
    navigateUp: () -> Boolean,
    event: (FilterEvent) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .fillMaxSize(),
//        horizontalAlignment = Alignment.CenterHorizontally,
//        verticalArrangement = Arrangement.Center
    ) {

        // -------------------- Categories --------------------
        Text(
            "Categories", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.allCategories.forEach { category ->
                FilterChip(
                    selected = category.id in state.selectedCategories,
                    onClick = { event(FilterEvent.ToggleCategory(category.id)) },
                    label = {
                        Text(
                            category.category_name,
                            color = if (category.id in state.selectedCategories) MaterialTheme.colorScheme.onPrimary
                            else MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                    )
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        // -------------------- Tags --------------------
        Text(
            "Tags", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 8.dp)
        )

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp), verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            state.allTags.forEach { tag ->
                FilterChip(
                    selected = tag.id in state.selectedTags,
                    onClick = { event(FilterEvent.ToggleTag(tag.id)) },
                    label = {
                        Text(
                            tag.tag_name,
                            color = if (tag.id in state.selectedTags) MaterialTheme.colorScheme.onSecondary
                            else MaterialTheme.colorScheme.secondary,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = androidx.compose.material3.FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.secondary,
                        containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f)
                    )
                )
            }
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = { onApply(state.selectedCategories, state.selectedTags) }, modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Filters")
        }
    }
}