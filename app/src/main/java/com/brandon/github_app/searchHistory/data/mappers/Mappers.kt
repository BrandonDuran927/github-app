package com.brandon.github_app.searchHistory.data.mappers

import com.brandon.github_app.core.local.SearchEntity
import com.brandon.github_app.core.model.Search

fun SearchEntity.toDomain(): Search {
    return Search(
        id = id,
        searchHistory = searchHistory
    )
}

fun List<SearchEntity>.toDomain(): List<Search> {
    return map { it.toDomain() }
}

fun Search.toEntity(): SearchEntity {
    return SearchEntity(
        id = id,
        searchHistory = searchHistory
    )
}