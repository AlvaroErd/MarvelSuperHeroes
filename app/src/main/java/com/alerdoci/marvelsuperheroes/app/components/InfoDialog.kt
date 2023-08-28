package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.alerdoci.marvelsuperheroes.app.common.utils.Constants.LOREM_IPSUM_SHORT
import com.alerdoci.marvelsuperheroes.app.theme.dimens

@Composable
fun InfoDialog(
    title: String? = "Message",
    desc: String? = "Your Message",
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss,
    ) {
        Box(
            modifier = Modifier
                .height(460.dp)
        ) {
            Column(
                modifier = Modifier
            ) {
                Spacer(modifier = Modifier.height(130.dp))
                Box(
                    modifier = Modifier
                        .wrapContentHeight()
                        .background(
                            color = MaterialTheme.colorScheme.surfaceContainerHigh,
                            shape = CutCornerShape(
                                topStart = MaterialTheme.dimens.custom24,
                                bottomEnd = MaterialTheme.dimens.custom24
                            )
                        )
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Spacer(modifier = Modifier.height(40.dp))

                        Text(
                            text = title!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.displayLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )

                        Text(
                            text = desc!!,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 15.dp, start = 25.dp, end = 25.dp)
                                .fillMaxWidth(),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                        )

                        Button(
                            onClick = onDismiss,
                            modifier = Modifier
                                .width(200.dp)
                                .clip(RoundedCornerShape(5.dp)),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onSurfaceVariant)
                        ) {
                            Text(
                                text = "Enjoy the app! \uD83D\uDE0E",
                                color = MaterialTheme.colorScheme.background,
                                style = MaterialTheme.typography.labelMedium.copy(letterSpacing = 1.sp),
                                )
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                    }
                }
            }
            LottieHeader(
                modifier = Modifier
                    .size(200.dp)
                    .align(Alignment.TopCenter)
            )
        }
    }
}

@PreviewLightDark
@Composable
fun InfoDialogPreview() {

    val infoDialog = remember { mutableStateOf(false) }

    InfoDialog(
        title = "Hi! I´m Álvaro",
        desc = LOREM_IPSUM_SHORT,
        onDismiss = {
            infoDialog.value = false
        }
    )

}