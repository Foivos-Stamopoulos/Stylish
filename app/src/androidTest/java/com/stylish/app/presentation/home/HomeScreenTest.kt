package com.stylish.app.presentation.home

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import com.stylish.app.HiltTestActivity
import com.stylish.app.R
import com.stylish.app.core.domain.model.Product
import com.stylish.app.home.presentation.HomeContract
import com.stylish.app.home.presentation.HomeScreen
import com.stylish.app.home.presentation.util.TestTags
import com.stylish.app.ui.theme.StylishTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class HomeScreenTest {

    @get: Rule(order = 1)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 2)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun home_screen_content_is_displayed_successfully() {

        composeTestRule.setContent {
            StylishTheme {
                HomeScreen(
                    state = state,
                    event = {}
                    )
            }
        }

        composeTestRule.onNodeWithTag(TestTags.SEARCH_BAR).assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.TOP_BAR).assertIsDisplayed()

        composeTestRule.onNodeWithText(composeTestRule.activity.getString(R.string.label_all_featured)).assertIsDisplayed()

        composeTestRule.onNodeWithText("Electronics").assertIsDisplayed()
        composeTestRule.onNodeWithText("Jewelery").assertIsDisplayed()
        composeTestRule.onNodeWithText("Men's Clothing").assertIsDisplayed()
        composeTestRule.onNodeWithText("Women's Clothing").assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.HORIZONTAL_PAGER).assertIsDisplayed()

        composeTestRule.onNodeWithText("Mens Cotton Jacket").assertIsDisplayed()

        composeTestRule.onNodeWithTag(TestTags.PROGRESS_BAR).assertIsNotDisplayed()
    }

    @Test
    fun progress_bar_is_displayed_when_home_screen_is_in_loading_state() {

        composeTestRule.setContent {
            StylishTheme {
                HomeScreen(
                    state = HomeContract.State(categories = emptyList(), products = emptyList(), isLoading = true),
                    event = {}
                )
            }
        }

        composeTestRule.onNodeWithTag(TestTags.PROGRESS_BAR).assertIsDisplayed()
    }

}

val state = HomeContract.State(
    categories = listOf("Electronics", "Jewelery", "Men's Clothing", "Women's Clothing"),
    products = listOf(
        Product(
            id = 1,
            title = "Mens Cotton Jacket",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99f,
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
        ),
        Product(
            id = 2,
            title = "Women's Cotton Jacket",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99f,
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
        ),
        Product(
            id = 3,
            title = "Children Cotton Jacket",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99f,
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
        ),
        Product(
            id = 4,
            title = "Mens Shoes",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99f,
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
        ),
        Product(
            id = 5,
            title = "Women's Shoes",
            description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
            price = 55.99f,
            category = "men's clothing",
            image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg"
        )
    ),
    isLoading = false
)