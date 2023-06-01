package com.alerdoci.marvelsuperheroes.app.common.states.error

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.components.LoadGif
import com.alerdoci.marvelsuperheroes.app.theme.dimens
import com.alerdoci.marvelsuperheroes.app.theme.spacing

@Composable
fun ErrorScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(MaterialTheme.spacing.small)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraMedium)
        ) {
            LoadGif(
                img = R.drawable.error_awkward_gif,
                modifier = Modifier
                    .size(MaterialTheme.dimens.custom200)
                    .clip(MaterialTheme.shapes.large)
            )

            Spacer(Modifier.height(MaterialTheme.spacing.extraMedium))

            Text(
                text = stringResource(R.string.error),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.error,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.semiSmall))

            Text(
                text = stringResource(R.string.try_reload),
                style = MaterialTheme.typography.bodyMedium,
            )

        }
    }
}

@Preview("Light Theme", showBackground = true)
@Preview("Dark Theme", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
fun ErrorScreenPreview() {
    ErrorScreen()
}