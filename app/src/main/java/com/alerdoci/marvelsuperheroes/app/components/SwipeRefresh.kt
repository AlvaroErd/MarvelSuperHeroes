package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun SwipeRefreshLazy() {
    val viewModel = viewModel<SwipeRefreshViewModel>()
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)

    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = viewModel::loadStuff,
        indicator = { state, refreshTrigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = refreshTrigger,
                backgroundColor = Color.Red,
                contentColor = Color.Blue
            )
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(100) {
                Text(
                    text = "Sample",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp)
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun SwipeRefreshLazyPreview() {
    SwipeRefreshLazy()
}
