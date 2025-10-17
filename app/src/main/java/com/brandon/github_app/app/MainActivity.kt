package com.brandon.github_app.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.brandon.github_app.app.ui.theme.GithubappTheme
import com.brandon.github_app.core.composables.NetworkStatusBar
import com.brandon.github_app.core.route.Search
import com.brandon.github_app.fileViewer.presentation.fileViewerNavGraph
import com.brandon.github_app.repoContents.presentation.repoContentsNavGraph
import com.brandon.github_app.search.presentation.searchNavGraph
import com.brandon.github_app.searchHistory.presentation.searchHistoryNavGraph
import com.brandon.github_app.listOfRepo.presentation.listOfRepoNavGraph
import com.example.beupdated.core.network.NetworkStatus
import com.example.beupdated.core.network.NetworkViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GithubappTheme (dynamicColor = false) {
                val networkViewModel: NetworkViewModel = hiltViewModel()
                val networkStatus by networkViewModel.networkStatus.collectAsStateWithLifecycle()

                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        val navController = rememberNavController()

                        NavGraph(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            networkStatus = networkStatus
                        )

                        // Floating network status at bottom
                        AnimatedVisibility(
                            visible = networkStatus != NetworkStatus.Available,
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(innerPadding)
                                .padding(16.dp),
                            enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                            exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                        ) {
                            NetworkStatusBar(networkStatus = networkStatus)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController,
    networkStatus: NetworkStatus
) {
    NavHost(
        navController = navController,
        startDestination = Search
    ) {
        searchNavGraph(
            modifier = modifier,
            navController = navController
        )

        listOfRepoNavGraph(
            navController = navController,
            networkStatus = networkStatus
        )

        repoContentsNavGraph(
            navController = navController
        )

        fileViewerNavGraph(
            navController = navController
        )

        searchHistoryNavGraph(
            navController = navController
        )
    }
}
