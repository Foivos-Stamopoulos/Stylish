package com.stylish.app.core.domain.model

import android.os.Parcelable
import com.stylish.app.core.data.dto.ProductPatchDto
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(

    val id: Int,

    val title: String,

    val price: Float,

    val category: String,

    val description: String,

    val image: String

): Parcelable

fun Product.toProductPatchDto(): ProductPatchDto {
    return ProductPatchDto(
        title,
        price,
        category,
        description,
        image
    )
}