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
    networkStatus: NetworkStatus
) {
    composable<UserRepos> {
        UserReposScreenCore(
            networkStatus = networkStatus,
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