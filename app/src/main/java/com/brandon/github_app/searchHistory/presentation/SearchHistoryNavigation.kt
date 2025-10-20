package com.brandon.github_app.searchHistory.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brandon.github_app.core.route.SearchHistory
import com.brandon.github_app.core.route.UserRepos

fun NavGraphBuilder.searchHistoryNavGraph(
    navController: NavController
) {
    composable<SearchHistory> {
        SearchHistoryScreenCore(
            onBackPress = { navController.navigateUp() },
            onSearchClick = { username ->
                navController.navigate(UserRepos(username)) {
                    popUpTo(SearchHistory) { inclusive = true }
                }
            }
        )
    }
}