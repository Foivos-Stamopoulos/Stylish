package com.stylish.app.home.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.stylish.app.core.domain.model.Product
import com.stylish.app.home.presentation.HomeContract
import com.stylish.app.home.presentation.preview_data.ProductsProvider
import com.stylish.app.ui.theme.StylishTheme

@Composable
fun ProductsSection(
    products: List<Product>,
    event: (HomeContract.Event.OnProductClick) -> Unit
) {
    LazyHorizontalGrid(
        modifier = Modifier.heightIn(max=500.dp),
        rows = GridCells.Fixed(2),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(products.size) { index ->
            ProductItem(
                product = products[index],
                event = { productId ->
                    event(productId)
                }
            )
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun ProductsSectionPreview(
    @PreviewParameter(ProductsProvider::class) products: List<Product>
) {
    StylishTheme {
        ProductsSection(
            products = products,
            event = {}
        )
    }
}