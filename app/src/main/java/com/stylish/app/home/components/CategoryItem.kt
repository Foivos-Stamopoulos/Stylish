package com.stylish.app.home.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.stylish.app.R
import com.stylish.app.ui.theme.StylishTheme

@Composable
fun CategoryItem(
    categoryName: String
) {
    Column(
        modifier = Modifier.width(IntrinsicSize.Min).clickable {  },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier.size(56.dp),
            painter = painterResource(id = R.drawable.ic_category),
            contentDescription = stringResource(id = R.string.content_description_category_image)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = categoryName,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelSmall
        )
    }

}

@Preview(showBackground = true)
@Composable
fun CategoryItemPreview() {
    StylishTheme {
        CategoryItem(categoryName = "Electronics")
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryItemLargeTitlePreview() {
    StylishTheme {
        CategoryItem(categoryName = "Women's clothing")
    }
}