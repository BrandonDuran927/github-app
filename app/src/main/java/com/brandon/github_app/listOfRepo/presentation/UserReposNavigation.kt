package com.brandon.github_app.listOfRepo.presentation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brandon.github_app.core.route.RepoContents
import com.brandon.github_app.core.route.UserRepos

fun NavGraphBuilder.userReposNavGraph(
    navController: NavController
) {
    composable<UserRepos> {
        UserReposScreenCore(
            onDetailsClick = { usersRepo ->
                navController.navigate(RepoContents(
                    usersRepo.owner.username,
                    usersRepo.name
                ))
            },
            onBackPress = {
                Log.d("UserReposNavGraph", "onBackPress: Pressed")
                navController.navigateUp()
            }
        )
    }
}