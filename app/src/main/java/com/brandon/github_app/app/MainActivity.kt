package com.brandon.github_app.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandon.github_app.core.route.Search
import com.brandon.github_app.fileViewer.presentation.fileViewerNavGraph
import com.brandon.github_app.repoContents.presentation.repoContentsNavGraph
import com.brandon.github_app.search.presentation.searchNavGraph
import com.brandon.github_app.ui.theme.GithubappTheme
import com.brandon.github_app.userRepos.presentation.userReposNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubappTheme (dynamicColor = false) {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val navController = rememberNavController()

                    NavGraph(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                    )
                }
            }
        }
    }
}

@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Search
    ) {
        searchNavGraph(
            modifier = modifier,
            navController = navController
        )

        userReposNavGraph(
            navController = navController
        )

        repoContentsNavGraph(
            modifier = modifier,
            navController = navController
        )

        fileViewerNavGraph(
            navController = navController
        )
    }
}
