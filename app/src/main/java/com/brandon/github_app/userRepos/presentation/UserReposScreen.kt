package com.brandon.github_app.userRepos.presentation

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.AccountTree
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import coil.compose.AsyncImage
import com.brandon.github_app.R
import com.brandon.github_app.core.model.Owner
import com.brandon.github_app.ui.theme.GithubappTheme
import com.brandon.github_app.userRepos.domain.model.UserRepo

@Composable
fun UserReposScreenCore(
    viewModel: UserReposViewModel = hiltViewModel<UserReposViewModel>(),
    onDetailsClick: (UserRepo) -> Unit,
    onBackPress: () -> Unit,
) {
    UserReposScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onDetailsClick = onDetailsClick,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserReposScreen(
    state: UserReposState,
    onAction: (UserReposAction) -> Unit,
    onDetailsClick: (UserRepo) -> Unit,
    onBackPress: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(onClick = {
                        onAction(UserReposAction.ViewUserPicture(true))
                    }) {
                        AsyncImage(
                            model = state.userRepos.firstOrNull()?.owner?.avatar_url
                                ?: R.drawable.github_icon,
                            contentDescription = "Profile Picture",
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(10))
                        )
                    }
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Logout,
                            contentDescription = "Logout",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    titleContentColor = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(horizontal = 24.dp)
        ) {
            val name = state.userRepos.firstOrNull()?.owner?.username
            val displayName = name?.takeIf { it.isNotBlank() } ?: state.username

            Text(
                text = "$displayName Repositories",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            if (state.isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(state.userRepos) { repo ->
                    RepoItem(repo = repo, onDetailsClick = onDetailsClick)
                }
            }
        }

        if (state.showUserPictureDialog) {
            Dialog(
                onDismissRequest = {
                    onAction(UserReposAction.ViewUserPicture(false))
                }
            ) {
                ProfilePictureDialog(
                    avatarUrl = state.userRepos.firstOrNull()?.owner?.avatar_url,
                    username = state.userRepos.firstOrNull()?.owner?.username
                        ?: state.username,
                    onDismiss = {
                        onAction(UserReposAction.ViewUserPicture(false))
                    }
                )
            }
        }
    }
}

@Composable
fun ProfilePictureDialog(
    avatarUrl: String?,
    username: String,
    onDismiss: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(bottom = 24.dp, top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Close button
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$username Picture",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 10.dp)
            )

            IconButton(
                onClick = onDismiss
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete_icon),
                    contentDescription = "Close",
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(32.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        AsyncImage(
            model = avatarUrl ?: R.drawable.github_icon,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(16.dp)),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun RepoItem(
    repo: UserRepo,
    onDetailsClick: (UserRepo) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onDetailsClick(repo) }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountTree,
                contentDescription = "Repository Tree",
                modifier = Modifier.size(32.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = repo.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = repo.description ?: "No description provided",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Stars",
                        modifier = Modifier.size(16.dp),
                        tint = Color.Yellow
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = repo.stargazers_count.toString(),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
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
        UserReposScreen(
            state = UserReposState(),
            onDetailsClick = {},
            onBackPress = {},
            onAction = {}
        )
    }
}

private val mockUpData = listOf(
    UserRepo(
        id = 1,
        name = "404-Error-Page-UI-Flutter",
        description = "404 Error Page(UI) Using Flutter asdf as fdasd f",
        owner = Owner(
            id = 1,
            username = "BrandonDuran927",
            avatar_url = ""
        ),
        stargazers_count = 10,
        language = "Dart"
    ),
    UserRepo(
        id = 2,
        name = "App-onboarding-UI-Flutter",
        description = "Onboarding App UI using Flutter",
        owner = Owner(
            id = 1,
            username = "BrandonDuran927",
            avatar_url = ""
        ),
        stargazers_count = 3,
        language = "Dart"
    )
)