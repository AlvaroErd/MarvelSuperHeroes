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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.components.ImageGif
import com.alerdoci.marvelsuperheroes.app.theme.montserratFont

@Composable
fun ErrorScreen() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(horizontal = 20.dp)
        ) {
            ImageGif(
                img = R.drawable.error_awkward_gif,
                imgGifModifier = Modifier
                    .size(200.dp)
                    .clip(MaterialTheme.shapes.large)
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.error),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontFamily = montserratFont,
                    color = MaterialTheme.colorScheme.error
                ),
            )

            Spacer(Modifier.height(5.dp))

            Text(
                text = stringResource(R.string.try_reload),
                style = MaterialTheme.typography.titleSmall,
                fontFamily = montserratFont,
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