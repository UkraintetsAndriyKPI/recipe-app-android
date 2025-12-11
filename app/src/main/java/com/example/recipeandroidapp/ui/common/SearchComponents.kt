package com.example.recipeandroidapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.recipeandroidapp.domain.model.Category
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.model.Tag
import com.example.recipeandroidapp.ui.theme.RecipeAndroidAppTheme

@Composable
fun FilterButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Rounded.MoreVert,
            contentDescription = "Filter",
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SearchBarPreview() {
    var text by remember { mutableStateOf("") }

    RecipeAndroidAppTheme {
        SearchBar(
            modifier = Modifier.fillMaxWidth(),
            searchText = text,
            readOnly = false,
            onTextChange = { text = it },
            onFilterClick = { },
            onSearch = { }
        )
    }
}


@Composable
fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxSize()) {

            // LEFT: Image
            Box(
                modifier = Modifier
                    .width(140.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (recipe.image != null) {
                    AsyncImage(
                        model = recipe.image,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Image,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            // RIGHT: Text content
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = recipe.categories.joinToString(separator = ", ") { it.category_name },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Row {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Cooking time",
                            tint = MaterialTheme.colorScheme.outline,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${recipe.cooking_time_min} min",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }

                if (recipe.tags.isNotEmpty()) {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        recipe.tags.forEach { tag ->
                            Box(
                                modifier = Modifier
                                    .height(24.dp)
                                    .wrapContentWidth()
                                    .background(
                                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(4.dp)
                                    )
                                    .padding(horizontal = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = tag.tag_name,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.65f)
                                )
                            }
                        }
                    }
                }


            }


        }


    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    // fake cats and recipe
    val sampleCategories = listOf(
        Category(id = 1, category_name = "Quick"),
        Category(id = 2, category_name = "Dessert")
    )

    val sampleTags = listOf(
        Tag(id = 1, tag_name = "TAG1"),
        Tag(id = 2, tag_name = "TAG2"),
        Tag(id = 3, tag_name = "TAG3"),
        Tag(id = 4, tag_name = "TAG4"),
        Tag(id = 5, tag_name = "TAG5"),
        Tag(id = 6, tag_name = "TAG6"),
    )

    val sampleRecipe = Recipe(
        id = 1,
        title = "Pancakes",
        description = "Fluffy pancakes for breakfast, " +
                "Lorem Ipsum is simply dummy text of " +
                "the printing and typesetting industry." +
                " Lorem Ipsum has been the industry's " +
                "standard dummy text ever since the 1500s, " +
                "when an unknown printer took a galley of type " +
                "and scrambled it to make a type specimen book. " +
                "It has survived not only five centuries, ",
        cooking_time_min = 20,
        creation_date = "2025-11-26T19:27:20.008993Z",
        is_published = true,
        image = null,
        categories = sampleCategories,
        tags = sampleTags
    )

    MaterialTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            RecipeCard(
                recipe = sampleRecipe,
                onClick = {}
            )
            RecipeCard(
                recipe = sampleRecipe,
                onClick = {}
            )
        }
    }
}


@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    readOnly: Boolean,
    onTextChange: (String) -> Unit,
    onSearchClick: (() -> Unit)? = null,
    onFilterClick: () -> Unit,
    onSearch: () -> Unit,
) {

    Row(
        modifier = modifier.padding(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SearchTextField(
            modifier = Modifier
                .weight(1f)
                .height(56.dp),
            text = searchText,
            onTextChange = onTextChange,
            readOnly = readOnly,
            onSearch = onSearch,
            onClick = onSearchClick
        )

        FilterButton(onClick = onFilterClick)
    }
}

@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    text: String,
    readOnly: Boolean = false,
    onClick: (() -> Unit)? = null,
    onTextChange: (String) -> Unit,
    onSearch: () -> Unit = {},
) {
    val interactionSource = remember {
        MutableInteractionSource()
    }
    val isClicked = interactionSource.collectIsPressedAsState().value

    LaunchedEffect(key1 = isClicked) {
        if (isClicked){
            onClick?.invoke()
        }
    }

    TextField(
        modifier = modifier.height(56.dp),
        value = text,
        readOnly = readOnly,
        onValueChange = onTextChange,
        placeholder = { Text("Search recipes …") },
        leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            errorIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(onSearch = { onSearch() }),
        interactionSource = interactionSource
    )
}