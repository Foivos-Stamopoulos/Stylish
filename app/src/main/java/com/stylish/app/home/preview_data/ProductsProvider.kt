package com.stylish.app.home.preview_data

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import com.stylish.app.core.domain.model.Product

class ProductsProvider : PreviewParameterProvider<List<Product>> {

    override val values: Sequence<List<Product>>
        get() = sequenceOf(
            listOf(
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
            )
        )

}