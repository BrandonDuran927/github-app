package com.brandon.github_app.listOfRepo.presentation

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brandon.github_app.core.route.RepoContents
import com.brandon.github_app.core.route.UserRepos
import com.example.beupdated.core.network.NetworkStatus

fun NavGraphBuilder.listOfRepoNavGraph(
    navController: NavController,
) {
    composable<UserRepos> {
        UserReposScreenCore(
            onDetailsClick = { usersRepo ->
                navController.navigate(RepoContents(
                    id = usersRepo.id,
                    owner = usersRepo.owner.username,
                    repoName = usersRepo.name
                ))
            },
            onBackPress = {
                navController.navigateUp()
            }
        )
    }
}