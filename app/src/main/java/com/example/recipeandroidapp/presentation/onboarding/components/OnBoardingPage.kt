package com.example.recipeandroidapp.presentation.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.example.recipeandroidapp.util.Dimens.MediumPadding1
import com.example.recipeandroidapp.util.Dimens.MediumPadding2
import com.example.recipeandroidapp.presentation.onboarding.Page
import com.example.recipeandroidapp.presentation.onboarding.pages


@Composable
fun OnBoardingPage(
    modifier: Modifier = Modifier, page: Page
) {
    Column(modifier = modifier) {
        Image(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            contentScale = ContentScale.Crop,
            painter = painterResource(id = page.image),
            contentDescription = "Description image"
        )
        Spacer(modifier = Modifier.height(MediumPadding1))
        Text(
            text = page.title,
            modifier = Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = page.description,
            modifier = Modifier.padding(horizontal = MediumPadding2),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ImagePreview0() {
    MaterialTheme {
        OnBoardingPage(
            page = pages[0]
        )
    }
}


@Preview(showBackground = true)
@Composable
fun ImagePreview1() {
    MaterialTheme {
        OnBoardingPage(
            page = pages[1]
        )
    }
}