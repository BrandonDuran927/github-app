package com.brandon.github_app.search.presentation

import android.content.res.Configuration
import android.graphics.drawable.shapes.Shape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.brandon.github_app.R
import com.brandon.github_app.ui.theme.GithubappTheme


@Composable
fun SearchScreenCore(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel<SearchViewModel>(),
    onSearchClick: (String) -> Unit
) {

    SearchScreen(
        modifier = modifier,
        state = viewModel.state,
        onAction = viewModel::onAction,
        onSearchClick = onSearchClick
    )
}

@Composable
private fun SearchScreen(
    modifier: Modifier = Modifier,
    state: SearchState,
    onAction: (SearchAction) -> Unit,
    onSearchClick: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Text(
            text = "GitHub API",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Bold
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.github_icon),
                contentDescription = "GitHub Logo",
                modifier = Modifier.size(120.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = state.query,
                onValueChange = {
                    onAction(SearchAction.OnSearchQueryChange(it))
                },
                modifier = Modifier.fillMaxWidth(),
                placeholder = { Text("Enter GitHub username") },
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))



            Button(
                onClick = {
                    if (state.query.isBlank()) {
                        onAction(SearchAction.OnSearchEmpty(true))
                    } else {
                        onAction(SearchAction.OnSearchEmpty(false))
                        onSearchClick(state.query)
                        onAction(SearchAction.ResetState)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Let's Explore this Guy Repos on GitHub!",
                    fontSize = 14.sp
                )
            }

            if (state.isQueryEmpty) {
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Please enter a GitHub username.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp,
                    modifier = Modifier
                        .padding(top = 4.dp, start = 4.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScreenPreview() {
    GithubappTheme(dynamicColor = false) {
        SearchScreen(
            state = SearchState(),
            onAction = {},
            onSearchClick = {}
        )
    }
}