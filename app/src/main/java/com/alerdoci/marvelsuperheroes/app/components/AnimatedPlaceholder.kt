package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.utils.ScrollAnimation
import com.alerdoci.marvelsuperheroes.app.common.utils.doWhenHasNextOrPrevious
import com.alerdoci.marvelsuperheroes.app.theme.RocGrotesk

@Composable
fun AnimatedPlaceholder(
    hints: List<String>,
    textStyle: FontFamily = RocGrotesk,
    textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
) {
    val iterator = hints.listIterator()

    val target by produceState(initialValue = hints.first()) {
        iterator.doWhenHasNextOrPrevious {
            value = it
        }
    }

    AnimatedContent(
        targetState = target,
        transitionSpec = { ScrollAnimation() }, label = ""
    ) { str ->
        Text(
            text = str,
            fontFamily = textStyle,
            color = textColor,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AnimatedPlaceholderPreview() {

    var textSearched by remember { mutableStateOf("") }
    var textActive by remember { mutableStateOf(false) }

    val hints = listOf(
        "Search your favourite topic 1",
        "Search your favourite topic 2",
        "Search your favourite topic 3",
    )

    SearchBar(
        query = textSearched,
        onQueryChange = { newTextSearched ->
            textSearched =
                newTextSearched
        },
        onSearch = {
            textActive = false
        },
        active = textActive,
        onActiveChange = {
            textActive = it
        },
        placeholder = {
            AnimatedPlaceholder(
                hints = hints,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
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
// Search screen
    }
}
