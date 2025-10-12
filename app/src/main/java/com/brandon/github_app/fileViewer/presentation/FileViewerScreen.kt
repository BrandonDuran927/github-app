package com.brandon.github_app.fileViewer.presentation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.res.Configuration
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.brandon.github_app.repoContents.presentation.ContentItem
import com.brandon.github_app.ui.theme.GithubappTheme
import androidx.core.graphics.toColorInt
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun FileViewerScreenCore(
    viewModel: FileViewerViewModel = hiltViewModel<FileViewerViewModel>(),
    onBackPress: () -> Unit
) {
    FileViewerScreen(
        state = viewModel.state,
        onAction = viewModel::onAction,
        onBackPress = onBackPress
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FileViewerScreen(
    state: FileViewerState,
    onAction: (FileViewerAction) -> Unit,
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    // Snackbar for feedback
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var generatedHtml by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(state.file, state.language) {
        if (state.file != null) {
            generatedHtml = withContext(Dispatchers.Default) {
                generateHighlightedHtml(
                    content = state.file.content,
                    language = state.language,
                    fileName = state.file.name
                )
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
            )
        },
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackPress) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.KeyboardArrowLeft,
                            contentDescription = "Back to repository contents",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            state.file?.content?.let { content ->
                                val clip = ClipData.newPlainText("code", content)
                                clipboardManager.setPrimaryClip(clip)

                                scope.launch {
                                    snackbarHostState.showSnackbar(
                                        message = "Code copied to clipboard",
                                        duration = SnackbarDuration.Short
                                    )
                                }
                            }
                        },
                        enabled = state.file?.content != null
                    ) {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "Copy contents of the file",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
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
                .padding(start = 24.dp, end = 24.dp, bottom = 16.dp)
        ) {
            Text(
                text = "Codes",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(16.dp))

            when {
                state.isLoading || (state.file != null && generatedHtml == null) ->  {
                    Box(
                        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                generatedHtml != null -> {
                    AndroidView(
                        factory = { ctx ->
                            WebView(ctx).apply {
                                settings.apply {
                                    javaScriptEnabled = true
                                    builtInZoomControls = true
                                    displayZoomControls = false
                                    loadWithOverviewMode = true
                                    useWideViewPort = true
                                    // Performance improvements
                                    cacheMode = WebSettings.LOAD_NO_CACHE
                                }
                                setBackgroundColor("#0d1117".toColorInt())
                            }
                        },
                        update = { webView ->
                            webView.loadDataWithBaseURL(
                                null,
                                generatedHtml!!,
                                "text/html",
                                "UTF-8",
                                null
                            )
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                else -> {
                    Text(
                        text = "No content available",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

private fun generateHighlightedHtml(
    content: String,
    language: String,
    fileName: String
): String {
    val escapedContent = content
        .replace("&", "&amp;")
        .replace("<", "&lt;")
        .replace(">", "&gt;")
        .replace("\"", "&quot;")
        .replace("'", "&#39;")

    return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=5.0, user-scalable=yes">
            <meta charset="UTF-8">
            <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/styles/github-dark.min.css">
            <script src="https://cdnjs.cloudflare.com/ajax/libs/highlight.js/11.9.0/highlight.min.js"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/highlightjs-line-numbers.js/2.8.0/highlightjs-line-numbers.min.js"></script>
            <style>
                * {
                    margin: 0;
                    padding: 0;
                    box-sizing: border-box;
                }
                
                body {
                    background-color: #0d1117;
                    font-family: 'SF Mono', Monaco, 'Cascadia Code', 'Roboto Mono', Consolas, 'Courier New', monospace;
                    font-size: 13px;
                    line-height: 1.6;
                    color: #c9d1d9;
                    padding: 0;
                    overflow-x: auto;
                }
                
                .header {
                    background-color: #161b22;
                    padding: 12px 16px;
                    border-bottom: 1px solid #21262d;
                    position: sticky;
                    top: 0;
                    z-index: 10;
                }
                
                .filename {
                    color: #c9d1d9;
                    font-size: 14px;
                    font-weight: 600;
                }
                
                .code-container {
                    padding: 16px;
                }
                
                pre {
                    margin: 0;
                    padding: 0;
                    background-color: transparent !important;
                }
                
                code {
                    display: block;
                    padding: 0 !important;
                    background-color: transparent !important;
                    font-family: 'SF Mono', Monaco, 'Cascadia Code', 'Roboto Mono', Consolas, 'Courier New', monospace;
                    font-size: 13px;
                    line-height: 1.6;
                    white-space: pre;
                    word-wrap: normal;
                    overflow-x: auto;
                }
                
                .hljs {
                    background-color: transparent !important;
                    color: #c9d1d9;
                }
                
                /* Line numbers styling */
                .hljs-ln-numbers {
                    text-align: right;
                    color: #6e7681;
                    border-right: 1px solid #21262d;                  
                    margin-right: 16px;
                    user-select: none;
                    vertical-align: top;
                    min-width: 50px;
                }
                
                .hljs-ln-code {
                    padding-left: 8px !important;
                }
                
                /* Make line numbers table layout work properly */
                .hljs-ln {
                    border-collapse: collapse;
                    width: 100%;
                }
                
                .hljs-ln tbody {
                    display: table;
                    width: 100%;
                }
                
                .hljs-ln-n {
                    width: 50px;
                }
            </style>
        </head>
        <body>
            <div class="header">
                <div class="filename">$fileName</div>
            </div>
            <div class="code-container">
                <pre><code class="language-$language">$escapedContent</code></pre>
            </div>
            <script>
                hljs.highlightAll();
                hljs.initLineNumbersOnLoad();
            </script>
        </body>
        </html>
    """.trimIndent()
}

@Preview(name = "Light Mode")
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun ScreenPreview() {
    GithubappTheme(dynamicColor = false) {
        FileViewerScreen(
            state = FileViewerState(),
            onAction = {},
            onBackPress = {}
        )
    }
}