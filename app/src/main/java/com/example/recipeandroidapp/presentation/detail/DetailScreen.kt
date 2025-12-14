package com.example.recipeandroidapp.presentation.detail

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.recipeandroidapp.domain.model.Ingredient
import com.example.recipeandroidapp.domain.model.Recipe
import com.example.recipeandroidapp.domain.model.Step
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    recipe: Recipe,
    ingredients: List<Ingredient> = emptyList(),
    steps: List<Step> = emptyList(),
    event: (DetailEvent) -> Unit,
    navigateUp: () -> Unit,
    viewModel: DetailViewModel,
) {
    val snackbarHostState = remember { SnackbarHostState() }

    // SNACKBAR LOGIC
    LaunchedEffect(viewModel.sideEffect) {
        viewModel.sideEffect?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onEvent(DetailEvent.RemoveSideEffect)
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = modifier.fillMaxSize()
        ) {
            DetailTopBar(
                onBookmarkClick = { event(DetailEvent.UpsertBookmarkRecipe(recipe = recipe)) },
                onBackClick = navigateUp,
                isBookmarked = viewModel.state.isBookmarked
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 24.dp)
            ) {
                // Image
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceVariant), contentAlignment = Alignment.Center
                    ) {
                        if (!recipe.image.isNullOrEmpty()) {
                            AsyncImage(
                                model = recipe.image,
                                contentDescription = recipe.title,
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Rounded.Image,
                                contentDescription = "No image",
                                tint = Color.Gray,
                                modifier = Modifier.size(64.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Title
                item {
                    Text(
                        text = recipe.title,
                        style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Cooking time + date
                item {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        // Cooking time
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Schedule, // clock icon
                                contentDescription = "Cooking time",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "${recipe.cooking_time_min} min", style = MaterialTheme.typography.bodyLarge
                            )
                        }

                        // Creation date
                        Box(modifier = Modifier
                            .fillMaxSize(),
                            contentAlignment = Alignment.CenterEnd,
                        ){
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.CalendarToday,
                                    contentDescription = "Creation date",
                                    tint = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = formatApiDate(recipe.creation_date),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Description
                item {
                    Text(
                        text = recipe.description,
                        style = MaterialTheme.typography.bodyLarge,
                        lineHeight = 22.sp,
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }

                // Categories
                if (recipe.categories.isNotEmpty()) {
                    item {
                        Text(
                            "Categories:",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            recipe.categories.forEach { cat ->
                                AssistChip(
                                    onClick = {}, label = {
                                    Text(
                                        cat.category_name,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer // readable text
                                    )
                                }, colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer, // soft background
                                    labelColor = MaterialTheme.colorScheme.onSecondaryContainer
                                )

                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Tags
                if (recipe.tags.isNotEmpty()) {
                    item {
                        Text(
                            "Tags:", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            recipe.tags.forEach { tag ->
                                SuggestionChip(
                                    onClick = {}, label = {
                                    Text(
                                        tag.tag_name, color = MaterialTheme.colorScheme.onTertiaryContainer
                                    )
                                }, colors = SuggestionChipDefaults.suggestionChipColors(
                                    containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                                    labelColor = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Ingredients
                if (viewModel.state.ingredients.isNotEmpty()) {
                    item {
                        Text(
                            "Ingredients:",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        viewModel.state.ingredients.forEach { ing ->
                            Text(
                                "• ${ing.quantity ?: "-"} ${ing.unit} ${ing.ingredient_name}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }

                // Steps
                if (viewModel.state.steps.isNotEmpty()) {
                    item {
                        Text(
                            "Steps:",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
                        )
                        viewModel.state.steps.forEach { step ->
                            Text(
                                "${step.step_number}. ${step.instruction}",
                                style = MaterialTheme.typography.bodyLarge,
                                lineHeight = 22.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }


        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            SnackbarHost(
                hostState = snackbarHostState,
            ) { snackbarData ->
                Snackbar(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    snackbarData = snackbarData,
                )
            }
        }
    }
}

private fun formatApiDate(dateString: String, pattern: String = "dd MMMM yyyy"): String {
    return try {
        val trimmedDate = dateString.replace(Regex("\\.(\\d{3})\\d+Z$"), ".$1Z")
        val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            .apply { timeZone = TimeZone.getTimeZone("UTC") }
            .parse(trimmedDate)
        if (date != null) SimpleDateFormat(pattern, Locale.getDefault()).format(date) else dateString
    } catch (e: Exception) {
        e.printStackTrace()
        dateString
    }
}


//@Preview(showBackground = true)
//@Composable
//fun DetailScreenPreview() {
//    val sampleRecipe = Recipe(
//        id = 1,
//        title = "Spaghetti Carbonara",
//        description = "A classic Italian pasta dish with eggs, cheese, pancetta, and pepper.",
//        cooking_time_min = 25,
//        creation_date = "2025-12-08",
//        is_published = true,
//        image = null,
//        categories = listOf(
//            Category(id = 1, category_name = "Italian"),
//            Category(id = 2, category_name = "Pasta")
//        ),
//        tags = listOf(
//            Tag(id = 1, tag_name = "Quick"),
//            Tag(id = 2, tag_name = "Dinner")
//        ),
//    )
//
//    val sampleIngredients = listOf(
//        Ingredient(ingredient_name = "Spaghetti", quantity = "200", unit = "g", position = 1),
//        Ingredient(ingredient_name = "Pancetta", quantity = "100", unit = "g", position = 2),
//        Ingredient(ingredient_name = "Parmesan", quantity = "50", unit = "g", position = 3),
//        Ingredient(ingredient_name = "Eggs", quantity = "2", unit = "pcs", position = 4)
//    )
//
//    val sampleSteps = listOf(
//        Step(step_number = 1, instruction = "Boil the spaghetti in salted water."),
//        Step(step_number = 2, instruction = "Fry the pancetta until crisp."),
//        Step(step_number = 3, instruction = "Mix eggs and parmesan in a bowl."),
//        Step(step_number = 4, instruction = "Combine spaghetti with pancetta and egg mixture off the heat."),
//        Step(step_number = 5, instruction = "Serve immediately with extra parmesan and pepper.")
//    )
//
//    DetailScreen(
//        recipe = sampleRecipe,
//        ingredients = sampleIngredients,
//        steps = sampleSteps,
//        event = {},
//        navigateUp = {}
//    )
//}