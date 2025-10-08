package com.brandon.github_app.repoContents.presentation

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brandon.github_app.core.route.FileViewer
import com.brandon.github_app.core.route.RepoContents

fun NavGraphBuilder.repoContentsNavGraph(
    modifier: Modifier = Modifier,
    navController: NavController
) {
    composable<RepoContents> {
        RepoContentsScreenCore(
            onDetailsClick = { repoDetail, ownerName, repoName ->
                if (repoDetail.type == "dir") {
                    navController.navigate(RepoContents(
                        owner = ownerName,
                        repoName = repoName,
                        path = repoDetail.path
                    ))
                } else {
                    navController.navigate(FileViewer(
                        owner = ownerName,
                        repoName = repoName,
                        filePath = repoDetail.path
                    ))
                }
            },
            onBackPress = {
                Log.d("RepoContentsNavGraph", "onBackPress: Pressed")
                navController.navigateUp()
            },
        )
    }
}