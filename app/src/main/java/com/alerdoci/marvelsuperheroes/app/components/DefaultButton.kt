package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.hapticClickable
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.rememberShakerState
import com.alerdoci.marvelsuperheroes.app.common.extensions.ModifierExtensions.shaker
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.black
import com.alerdoci.marvelsuperheroes.app.theme.MarvelColors.white

@Composable
fun DefaultButton(
    text: String,
    containerColor: Color,
    contentColor: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val shakerController = rememberShakerState()
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .shaker(shakerController)
            .hapticClickable(HapticFeedbackType.LongPress) { onClick },
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor, contentColor = contentColor
        ),
//        onLongClick = {
//            haptics.performHapticFeedback(HapticFeedbackType.LongPress)
//        },
        onClick = onClick,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .padding(8.dp),
            style = MaterialTheme.typography.titleSmall,
        )
    }
}

@Preview
@Composable
fun DefaultButtonPreview(){
DefaultButton(
text = "Whoop whop",
containerColor = white,
contentColor = black,
) {}
}
