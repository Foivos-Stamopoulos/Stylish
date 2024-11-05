package com.stylish.app.core.data.mapper

import com.stylish.app.core.data.dto.ProductDto
import com.stylish.app.core.data.util.EntityMapper
import com.stylish.app.core.domain.model.Product
import javax.inject.Inject

class ProductMapper @Inject constructor(): EntityMapper<ProductDto, Product> {

    override fun mapFromEntity(entity: ProductDto): Product {
        return Product(
            entity.id,
            entity.title,
            entity.price,
            entity.category,
            entity.description,
            entity.image
        )
    }

    override fun mapToEntity(domainModel: Product): ProductDto {
        return ProductDto(
            domainModel.id,
            domainModel.title,
            domainModel.price,
            domainModel.category,
            domainModel.description,
            domainModel.image
        )
    }


}