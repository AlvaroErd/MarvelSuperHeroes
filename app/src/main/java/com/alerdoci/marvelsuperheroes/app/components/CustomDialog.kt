package com.alerdoci.marvelsuperheroes.app.components

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.alerdoci.marvelsuperheroes.R

@Composable
fun CustomDialog(openDialogCustom: MutableState<Boolean>) {
    Dialog(onDismissRequest = { openDialogCustom.value = false }) {
        CustomDialogUI(openDialogCustom = openDialogCustom)
    }
}

//Layout
@Composable
fun CustomDialogUI(modifier: Modifier = Modifier, openDialogCustom: MutableState<Boolean>) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 10.dp)
//        .size(280.dp, 240.dp)
            .clip(
                RoundedCornerShape(
                    topStart = 30.dp,
                    topEnd = 0.dp,
                    bottomStart = 0.dp,
                    bottomEnd = 30.dp
                )
            ),
    ) {

//        Image(
//            painter = painterResource(id = R.drawable.bg_planet),
//            contentDescription = "",
//            contentScale = ContentScale.Crop,
//            modifier = Modifier
//                .height(300.dp)
//                .alpha(0.2f)
//                .fillMaxSize()
//        )
        Column(
//            modifier.background(Color.White)
        ) {

            //.......................................................................
            Image(
                painter = painterResource(id = R.drawable.ic_baseline_notifications_24),
                contentDescription = null, // decorative
                contentScale = ContentScale.Fit,
                colorFilter = ColorFilter.tint(
                    color = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .padding(top = 35.dp)
                    .height(70.dp)
                    .fillMaxWidth(),

                )

            Column(modifier = Modifier.padding(16.dp)) {
                androidx.compose.material3.Text(
                    text = "Get Updates",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Allow Permission to send you notifications when new flavor added.",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            //.......................................................................
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        "Not Now",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                    )
                }
                androidx.compose.material3.TextButton(onClick = {
                    openDialogCustom.value = false
                }) {
                    Text(
                        "Allow",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.W900,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(top = 12.dp, bottom = 12.dp)
                    )
                }
            }
        }
    }
}


@SuppressLint("UnrememberedMutableState")
@Preview(name = "Custom Dialog")
@Composable
fun MyDialogUIPreview() {
    CustomDialogUI(openDialogCustom = mutableStateOf(false))
}
