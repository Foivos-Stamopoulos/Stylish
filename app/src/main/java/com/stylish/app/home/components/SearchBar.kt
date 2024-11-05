package com.stylish.app.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stylish.app.R
import com.stylish.app.ui.theme.StylishTheme

@Composable
fun SearchBar() {
    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(White)
            .border(1.dp, White, RoundedCornerShape(4.dp)),
        placeholder = {
            Text(
                text = stringResource(id = R.string.hint_search_bar),
                style = MaterialTheme.typography.bodyMedium,
                color = colorResource(id = R.color.search_bar)
            )
        },
        leadingIcon = {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = stringResource(id = R.string.content_description_search),
                colorFilter = ColorFilter.tint(colorResource(id = R.color.search_bar))
            )
        },
        value = "",
        singleLine = true,
        readOnly = true,
        enabled = false,
        onValueChange = { newValue ->
            // Do nothing
        }
    )
}

@Preview(showSystemUi = true)
@Composable
fun SearchBarPreview() {
    StylishTheme {
        SearchBar()
    }
}