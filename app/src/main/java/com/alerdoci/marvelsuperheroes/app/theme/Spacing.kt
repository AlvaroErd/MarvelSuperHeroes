package com.alerdoci.marvelsuperheroes.app.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
data class Spacing(
    val zero: Dp = 0.dp,
    val extraTiny: Dp = 2.dp,
    val extraSmall: Dp = 4.dp,
    val tiny: Dp = 5.dp,
    val semiSmall: Dp = 6.dp,
    val small: Dp = 8.dp,
    val extraMedium: Dp = 10.dp,
    val semiMedium: Dp = 12.dp,
    val medium: Dp = 16.dp,
    val semiLarge: Dp = 20.dp,
    val large: Dp = 24.dp,
    val extraLarge: Dp = 32.dp,
    val huge: Dp = 48.dp,
    val extraHuge: Dp = 64.dp
)

val LocalSpacing = compositionLocalOf { Spacing() }

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

@Preview
@Composable
fun SpacingPreview() {
    Column(modifier = Modifier.fillMaxHeight()) {

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.zero)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: Zero")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraTiny)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: extraTiny")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraSmall)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: extraSmall")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.tiny)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: tiny")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.semiSmall)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: semiSmall")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: small")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraMedium)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: extraMedium")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.semiMedium)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: semiMedium")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: medium")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.semiLarge)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: semiLarge")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.large)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: large")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraLarge)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: extraLarge")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.huge)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: huge")
            }
        }

        Box(modifier = Modifier.padding(bottom = MaterialTheme.spacing.extraHuge)) {
            Box(
                modifier = Modifier
                    .width(300.dp)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Padding bottom: extraHuge")
            }
        }

    }
}
