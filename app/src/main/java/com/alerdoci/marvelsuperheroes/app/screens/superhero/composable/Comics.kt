package com.alerdoci.marvelsuperheroes.app.screens.superhero.composable

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.red_800
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.model.features.superherocomic.ModelComicsSuperHeroList
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun ComicsList(comicListItems: List<ModelComicsSuperHeroList>) {

    Column {
        val inputDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        fun Date.toStringFormatted(): String {
            return outputDateFormat.format(this)
        }
        LazyRow(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraMedium)) {
            items(comicListItems) { item ->
                for (i in 0 until item.data?.results?.size!!) {
                    val comicImage = item.data.results[i].image
                    val comicTitle = item.data.results[i].title
//                    val comicDate =
//                        item.data.results[i].dates?.get(0)?.date.let { inputDateFormat.parse(it) }
//                            ?.toStringFormatted()

                    ComicCard(
                        item = item,
                        comicImage = comicImage.toString(),
                        comicTitle = comicTitle.toString(),
                        comicDate = ""
                    )
                }

            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComicCard(
    item: ModelComicsSuperHeroList,
    comicImage: String,
    comicTitle: String,
    comicDate: String
) {
    Box(
        modifier = Modifier
            .height(220.dp)
            .width(170.dp)
            .padding(end = 14.dp),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = comicImage,
            contentDescription =
            stringResource(
                id = R.string.photo_content_description,
                comicTitle
            ),
            loading = {
                Box(modifier = Modifier.padding(MaterialTheme.spacing.extraHuge)) {
                    CircularProgressIndicator(
                        color = red_800
                    )
                }
            },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(CutCornerShape(topStart = MaterialTheme.spacing.extraMedium))
                .fillMaxSize()
                .drawWithCache {
                    onDrawWithContent {
                        drawContent()
                        drawRect(
                            Brush.verticalGradient(
                                0.5f to MarvelColors.black.copy(alpha = 0F),
                                1F to MarvelColors.black
                            )
                        )
                    }
                },
        )

        Text(
            text = comicTitle,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.extraLarge
                )
                .align(Alignment.BottomCenter)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    initialDelayMillis = 3000
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
        Text(
            text = comicDate,
            modifier = Modifier
                .padding(
                    start = MaterialTheme.spacing.small,
                    end = MaterialTheme.spacing.small,
                    bottom = MaterialTheme.spacing.small
                )
                .align(Alignment.BottomCenter)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    initialDelayMillis = 3000
                ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White
        )
    }
}
