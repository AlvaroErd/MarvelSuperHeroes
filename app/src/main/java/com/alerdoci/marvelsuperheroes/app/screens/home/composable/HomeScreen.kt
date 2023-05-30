package com.alerdoci.marvelsuperheroes.app.screens.home.composable

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.screens.home.viewmodel.HomeViewModel
import com.alerdoci.marvelsuperheroes.app.theme.red_800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavHostController,
//    onItemClick: (String) -> Unit),
    viewModel: HomeViewModel = hiltViewModel()
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        val superHeroListState by viewModel.superHeroes.collectAsStateWithLifecycle()
        var textSearched by remember { mutableStateOf("") }
        var textActive by remember { mutableStateOf(false) }
        val context = LocalContext.current
        Scaffold(
            modifier = Modifier
                .fillMaxHeight()
                .background(red_800)
        ) { paddingValues ->
            Column(modifier = Modifier.fillMaxSize())
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(paddingValues = paddingValues)
                        .background(red_800)
                        .padding(bottom = 16.dp, top = 10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    SearchBar(
                        query = textSearched,
                        onQueryChange = { newTextSearched ->
                            textSearched =
                                newTextSearched
                        },
                        onSearch = {
                            textActive = false
//                                textSearched = ""
                            Toast.makeText(
                                context,
                                "Item searched: $textSearched",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        },
                        active = textActive,
                        onActiveChange = {
                            textActive = it
                        },
                        placeholder = { Text(text = stringResource(R.string.search_superhero)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search icon"
                            )
                        },
                        colors = SearchBarDefaults.colors(dividerColor = MaterialTheme.colorScheme.primary),
                        trailingIcon = {
                            if (textActive) {
                                IconButton(onClick = {
                                    if (textSearched.isNotEmpty()) {
                                        textSearched = ""
                                    } else {
                                        textActive = false
                                    }
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close icon",
                                    )
                                }
                            }
                        },
                    )
                    {
//                    if (textSearched.isNotEmpty()) {
//                        val filteredSuperhero =
//                            ((superHeroListState as ResourceState.Success).data as List<ModelResult>).filter {
//                                it.contains(
//                                    textSearched,
//                                    true
//                                ) as List
//                            }
//                        LazyColumn {
//                            items(filteredSuperhero) {
//                                Text(text = textSearched, modifier = Modifier.clickable {
//                                    Toast.makeText(
//                                        context,
//                                        "Item clicked: $textSearched",
//                                        Toast.LENGTH_LONG
//                                    ).show()
//                                })
//                            }
//                        }
//                    }
                    }
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                )
                {
                    DiagonalDivider(modifier = Modifier.weight(1f))
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(14.dp)
                )
                {
                    Text(text = "Surface")
                }
            }
        }
    }
}


@Composable
fun DiagonalDivider(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val width = size.width
                val height = size.height
                val path = Path().apply {
                    moveTo(0f, 0f)
                    lineTo(width, 0f)
                    lineTo(width * 0f, height)
                    lineTo(0f, height)
                    close()
                }
                drawPath(path, color = red_800)
            }
    ) {
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    HomeScreen(navController = navController)
}