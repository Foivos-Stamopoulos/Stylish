package com.stylish.app.core.data.mapper

import com.stylish.app.core.data.dto.UserLoginDto
import com.stylish.app.core.data.util.EntityMapper
import com.stylish.app.core.domain.model.UserLogin
import javax.inject.Inject

class UserLoginMapper @Inject constructor() : EntityMapper<UserLoginDto, UserLogin> {

    override fun mapFromEntity(entity: UserLoginDto): UserLogin {
        return UserLogin(
            token = entity.token
        )
    }

    override fun mapToEntity(domainModel: UserLogin): UserLoginDto {
        return UserLoginDto(
            token = domainModel.token
        )
    }


}