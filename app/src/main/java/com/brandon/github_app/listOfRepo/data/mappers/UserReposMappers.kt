package com.brandon.github_app.listOfRepo.data.mappers

import com.brandon.github_app.core.model.Owner
import com.brandon.github_app.listOfRepo.data.remote.respond.UserRepoDto
import com.brandon.github_app.listOfRepo.domain.model.UserRepo

fun UserRepoDto.toDomain(): UserRepo {
    return UserRepo(
        id = id,
        name = name,
        description = description,
        owner = Owner(
            avatar_url = owner.avatar_url,
            id = owner.id,
            username = owner.login
        ),
        stargazers_count = stargazers_count,
        language = language,
        pushed_at = pushed_at,
        updated_at = updated_at
    )
}

fun List<UserRepoDto>.toDomain(): List<UserRepo> {
    return map { it.toDomain() }
}