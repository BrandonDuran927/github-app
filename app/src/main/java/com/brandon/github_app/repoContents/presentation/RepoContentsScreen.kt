package com.brandon.github_app.repoContents.presentation

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.brandon.github_app.R
import com.brandon.github_app.app.ui.theme.GithubappTheme
import com.brandon.github_app.repoContents.domain.model.RepoContentsItem

@Composable
fun RepoContentsScreenCore(
    viewModel: RepoContentsViewModel = hiltViewModel<RepoContentsViewModel>(),
    onDetailsClick: (RepoContentsItem, String, String, Int) -> Unit,
    onBackPress: () -> Unit
) {
    RepoContentScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onDetailsClick = onDetailsClick,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RepoContentScreen(
    state: RepoContentsState,
    onDetailsClick: (RepoContentsItem, String, String, Int) -> Unit,
    onAction: (RepoContentsAction) -> Unit,
    onBackPress: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "Back to repositories",
                            modifier = Modifier.size(32.dp),
                        )
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            Text(
                text = state.repoName,
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(state.repoContents) { repo ->
                    ContentItem(
                        repo = repo,
                        onDetailsClick = onDetailsClick,
                        ownerName = state.ownerName,
                        repoName = state.repoName,
                        repoId = state.repoId ?: -1
                    )
                }
            }
        }
    }
}

@Composable
fun ContentItem(
    repo: RepoContentsItem,
    onDetailsClick: (RepoContentsItem, String, String, Int) -> Unit,
    ownerName: String,
    repoName: String,
    repoId: Int
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetailsClick(repo, ownerName, repoName, repoId) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                painter = if (repo.type == "dir") painterResource(R.drawable.folder_icon) else painterResource(
                    R.drawable.file_icon
                ),
                contentDescription = "Repository Tree",
                modifier = Modifier.size(30.dp),
                tint = MaterialTheme.colorScheme.onPrimary
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = repo.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = repo.type,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        Icon(
            imageVector = Icons.Filled.ArrowCircleRight,
            contentDescription = "Repository Details",
            modifier = Modifier.size(32.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScreenPreview() {
    GithubappTheme(dynamicColor = false) {
        RepoContentScreen(
            state = RepoContentsState(
                repoContents = listOf(
                    RepoContentsItem(
                        repoId = 1,
                        sha = "123",
                        name = "Android Shop",
                        path = "",
                        type = "dir"
                    ),
                    RepoContentsItem(
                        repoId = 2,
                        sha = "1233",
                        name = "README.md",
                        path = "",
                        type = "file"
                    )
                )
            ),
            onAction = {},
            onDetailsClick = { no, op, h, i ->

            },
            onBackPress = {}
        )
    }
}