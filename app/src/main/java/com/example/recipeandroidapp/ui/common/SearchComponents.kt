package com.example.recipeandroidapp.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.recipeandroidapp.ui.theme.RecipeAndroidAppTheme

@Composable
fun FilterButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier.Companion
            .size(48.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clickable { onClick() },
        contentAlignment = Alignment.Companion.Center
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
            searchText = text,
            onTextChange = { text = it },
            onFilterClick = { /* preview click */ }
        )
    }
}


@Composable
fun RecipeCard(
    imageUrl: String? = null,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .height(140.dp)
            .padding(vertical = 4.dp)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = androidx.compose.foundation.shape.RoundedCornerShape(16.dp)
    ) {
        Row(modifier = Modifier.Companion.fillMaxSize()) {

            // LEFT: Image
            Box(
                modifier = Modifier.Companion
                    .width(140.dp)
                    .fillMaxHeight()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                if (imageUrl != null) {
//                    AsyncImage(
//                        model = imageUrl,
//                        contentDescription = null,
//                        modifier = Modifier.fillMaxSize()
//                    )
                } else {
                    Icon(
                        imageVector = Icons.Rounded.Image,
                        contentDescription = null,
                        tint = Color.Companion.Gray,
                        modifier = Modifier.Companion
                            .size(48.dp)
                            .align(Alignment.Companion.Center)
                    )
                }
            }

            // RIGHT: Text content
            Column(
                modifier = Modifier.Companion
                    .fillMaxHeight()
                    .padding(16.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = "title",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.Companion.height(4.dp))
                    Text(
                        text = "category",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Text(
                    text = "cookTime",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RecipeCardPreview() {
    MaterialTheme {
        Column(modifier = Modifier.Companion.padding(16.dp)) {
            RecipeCard(
                imageUrl = null,
                onClick = {}
            )
        }
    }
}


@Composable
fun SearchBar(searchText: String, onTextChange: (String) -> Unit, onFilterClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.Companion.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = searchText,
            onValueChange = onTextChange,
            placeholder = { Text("Search recipes…") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier.Companion
                .weight(1f)
                .height(56.dp),
            shape = androidx.compose.foundation.shape.RoundedCornerShape(28.dp),
            singleLine = true,
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Companion.Transparent,
                unfocusedIndicatorColor = Color.Companion.Transparent,
                disabledIndicatorColor = Color.Companion.Transparent
            ),
        )

        FilterButton(onClick = onFilterClick)
    }
}