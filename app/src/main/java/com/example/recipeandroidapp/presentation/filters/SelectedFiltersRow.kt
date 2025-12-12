package com.example.recipeandroidapp.presentation.filters

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Tag

@Composable
fun SelectedFiltersRow(
    selectedCategories: Set<Int>,
    selectedTags: Set<Int>,
    allCategories: Set<Category>,
    allTags: Set<Tag>,
) {
    val selectedCategoryItems = allCategories.filter { it.id in selectedCategories }
    val selectedTagItems = allTags.filter { it.id in selectedTags }


    if (selectedCategoryItems.isEmpty() && selectedTagItems.isEmpty()) return

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier
            .padding(4.dp)
            .fillMaxWidth()
    ) {
        // Categories
        items(selectedCategoryItems) { category ->
            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = category.category_name,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // Tags
        items(selectedTagItems) { tag ->
            Box(
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.25f),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = tag.tag_name,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun SelectedFiltersRowPreview() {
    val categories = setOf(
        Category(id = 1, category_name = "Breakfast"),
        Category(id = 2, category_name = "Lunch"),
        Category(id = 3, category_name = "Dinner")
    )

    val tags = setOf(
        Tag(id = 1, tag_name = "Healthy"), Tag(id = 2, tag_name = "Quick"), Tag(id = 3, tag_name = "Protein")
    )

    SelectedFiltersRow(
        selectedCategories = setOf(1, 3),
        selectedTags = setOf(2),
        allCategories = categories,
        allTags = tags,)
}
