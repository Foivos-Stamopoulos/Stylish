package com.stylish.app.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.stylish.app.R
import com.stylish.app.home.components.CategoriesSection
import com.stylish.app.home.components.ProductsSection
import com.stylish.app.home.components.PromotionsHorizontalPager
import com.stylish.app.home.components.SearchBar
import com.stylish.app.home.preview_data.HomeStateProvider
import com.stylish.app.ui.theme.StylishTheme

@Composable
fun HomeScreen(
    state: HomeContract.State,
    event: (HomeContract.Event) -> Unit,
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() }
) {
    Scaffold(
        topBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp)) {
                Image(
                    modifier = Modifier
                        .size(111.dp, 31.dp)
                        .align(Alignment.Center),
                    imageVector = ImageVector.vectorResource(id = R.drawable.app_logo) ,
                    contentDescription = stringResource(id = R.string.app_name)
                )
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(40.dp))
                        .size(40.dp)
                        .align(Alignment.CenterEnd)
                        .clickable { },
                    painter = painterResource(id = R.drawable.ic_user_profile) ,
                    contentDescription = stringResource(id = R.string.app_name)
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)) {

            if (!(state.isLoading && state.categories.isEmpty() && state.products.isEmpty())) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    item {
                        Spacer(modifier = Modifier.height(16.dp))
                        SearchBar()
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        CategoriesSection(categories = state.categories)
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    item {
                        PromotionsHorizontalPager()
                    }
                    item {
                        ProductsSection(
                            products = state.products,
                            event = { onProductClick ->
                                event(HomeContract.Event.OnProductClick(onProductClick.id))
                            }
                        )
                    }

                }
            }

            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.Center),
                    strokeWidth = 2.dp
                )
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview(
    @PreviewParameter(HomeStateProvider::class) state: HomeContract.State
) {
    StylishTheme {
        HomeScreen(
            state = state,
            event = {}
        )
    }
}