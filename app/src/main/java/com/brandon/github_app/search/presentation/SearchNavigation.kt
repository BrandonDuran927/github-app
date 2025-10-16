package com.brandon.github_app.search.presentation

import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brandon.github_app.core.route.Search
import com.brandon.github_app.core.route.SearchHistory
import com.brandon.github_app.core.route.UserRepos

fun NavGraphBuilder.searchNavGraph(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    composable<Search> {
        SearchScreenCore(
            modifier = modifier,
            onSearchClick = { username ->
                navController.navigate(UserRepos(username))
            },
            onSearchHistoryClick = {
                navController.navigate(SearchHistory)
            }
        )
    }
}