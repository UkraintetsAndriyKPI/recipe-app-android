package com.example.recipeandroidapp.presentation.filters

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FilterScreen(
    modifier: Modifier = Modifier,
    viewModel: FilterViewModel,
    onApply: (selectedCategories: Set<Int>, selectedTags: Set<Int>) -> Unit,
    navigateUp: () -> Boolean,
    event: (FilterEvent) -> Unit,
) {
    val state by viewModel.state.collectAsState()

    Column(
        modifier = modifier
            .padding(16.dp)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {

        Text("Categories", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        state.allCategories.forEach { category ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = category.id in state.selectedCategories,
                    onCheckedChange = {
                        event(FilterEvent.ToggleCategory(category.id))
                    }
                )
                Text(category.category_name, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text("Tags", style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        state.allTags.forEach { tag ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = tag.id in state.selectedTags,
                    onCheckedChange = {
                        event(FilterEvent.ToggleTag(tag.id))
                    }
                )
                Text(tag.tag_name, modifier = Modifier.padding(start = 8.dp))
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { onApply(state.selectedCategories,
                state.selectedTags) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Apply Filters")
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FilterScreenPreview() {
//    val categories = listOf(
//        Category(1, "Breakfast"),
//        Category(2, "Lunch"),
//        Category(3, "Dinner")
//    )
//
//    val tags = listOf(
//        Tag(1, "Quick"),
//        Tag(2, "Vegan"),
//        Tag(3, "Dessert")
//    )
//
//    val selectedCategories = remember { mutableStateOf(setOf(1)) }
//    val selectedTags = remember { mutableStateOf(setOf(2)) }
//
//    val onApply: (Set<Int>, Set<Int>) -> Unit = { categories, tags ->
//        println("Applied Categories: $categories, Tags: $tags")
//    }
//
//    FilterScreen(
//        modifier = Modifier.fillMaxSize(),
//        onApply = onApply,
//        navigateUp = { false },
//        event = { /*TODO()*/ },
//    )
//}