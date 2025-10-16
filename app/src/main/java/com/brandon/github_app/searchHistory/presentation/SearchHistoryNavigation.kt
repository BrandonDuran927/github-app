package com.brandon.github_app.searchHistory.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brandon.github_app.core.route.SearchHistory

fun NavGraphBuilder.searchHistoryNavGraph(
    navController: NavController
) {
    composable<SearchHistory> {
        SearchHistoryScreenCore(
            onBackPress = { navController.navigateUp() }
        )
    }
}