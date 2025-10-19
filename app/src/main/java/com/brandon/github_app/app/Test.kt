package com.brandon.github_app.app

private data class Tmp (
    val id: Int,
    val name: String,
)

private val mockList = listOf(
    Tmp(id = 1, name = "Brandon"),
    Tmp(id = 2, name = "Jewel")
)

private val mockListRemote = listOf(
    Tmp(id = 1, name = "Brandon Duran"),
    Tmp(id = 2, name = "Jewel Duran"),
    Tmp(id = 3, name = "Pmmm")
)

private val mockMap = mockList.associateBy { it.id }

fun main() {
    mockListRemote.forEach { remoteRepo ->
        val localRepo = mockMap[remoteRepo.id]
        println(localRepo)

    }
}