package com.brandon.github_app.fileViewer.presentation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.brandon.github_app.core.route.FileViewer

fun NavGraphBuilder.fileViewerNavGraph(
    navController: NavController
) {
    composable<FileViewer> {
        FileViewerScreenCore(
            onBackPress = {
                navController.navigateUp()
            }
        )
    }
}