package com.alerdoci.marvelsuperheroes.app.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R

@ExperimentalMaterial3Api
@Composable
fun CompactWeatherCard(
    nameOfLocation: String,
    shortDescription: String,
    @DrawableRes shortDescriptionIcon: Int,
    weatherInDegrees: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val weatherWithDegreesSuperscript = remember(weatherInDegrees) {
        "$weatherInDegrees°"
    }
    OutlinedCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // add weight modifier to the column composable to ensure
            // that the composable is measured after the other
            // composable is measured.
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = nameOfLocation,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium
                )
                ShortWeatherDescriptionWithIconRow(
                    shortDescription = shortDescription,
                    iconRes = shortDescriptionIcon
                )
            }
            Text(
                text = weatherWithDegreesSuperscript,
                style = MaterialTheme.typography.displayMedium
            )
        }
    }
}

@Composable
private fun ShortWeatherDescriptionWithIconRow(
    shortDescription: String,
    @DrawableRes iconRes: Int,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            imageVector = ImageVector.vectorResource(id = iconRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
        Text(
            text = shortDescription,
            maxLines = 1,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Normal
        )
    }
}

@Preview
@ExperimentalMaterial3Api
@Composable
fun CompactWeatherCardPreview() {
    val modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)

    val onClick: () -> Unit = {}

    CompactWeatherCard(
        nameOfLocation = "City Name",
        shortDescription = "Partly Cloudy",
        shortDescriptionIcon = R.drawable.ic_partly_cloudy,
        weatherInDegrees = "25",
        onClick = onClick,
        modifier = modifier
    )
}
