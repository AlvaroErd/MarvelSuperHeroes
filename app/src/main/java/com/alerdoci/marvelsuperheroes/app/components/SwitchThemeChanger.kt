package com.alerdoci.marvelsuperheroes.app.components

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Yellow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alerdoci.marvelsuperheroes.R

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SwitchThemeChanger() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = "Normal Switch With Icon Change")
        SwitchComponent()
        Spacer(modifier = Modifier.height(50.dp))

        Text(text = "Custom Switch With Animation")

        CustomSwitchComponent()

    }

}


@Preview(showBackground = true)
@Composable
fun SwitchComponent() {

    var isChecked by remember { mutableStateOf(false) }

    Switch(checked = isChecked, onCheckedChange = { isChecked = it }, thumbContent = {

        if (isChecked) {
            Icon(
                painter = painterResource(id = R.drawable.ic_sun),
                contentDescription = "Day Mode",
                modifier = Modifier.padding(5.dp),
                tint = Color.White
            )
        } else {
            Icon(
                painter = painterResource(id = R.drawable.ic_moon),
                contentDescription = "Night Mode",
                modifier = Modifier.padding(5.dp),
                tint = Color.White
            )
        }

    }, colors = SwitchDefaults.colors(
        checkedTrackColor = Blue,
        uncheckedTrackColor = Blue.copy(0.8f),
        uncheckedBorderColor = Blue.copy(0.8f),
        checkedThumbColor = Yellow,
        uncheckedThumbColor = Gray
    ), modifier = Modifier
        .scale(2f)
        .padding(30.dp)
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun CustomSwitchComponent() {

    var isSwitchOn by remember { mutableStateOf(false) }

    val bgColor: Color by animateColorAsState(
        if (isSwitchOn) Blue else Blue.copy(0.8f),
        animationSpec = tween(300, easing = LinearEasing),
        label = "Animated Switch Color"
    )

    val thumbColor: Color by animateColorAsState(
        if (isSwitchOn) Yellow else Gray,
        animationSpec = tween(300, easing = LinearEasing),
        label = "Animated Switch Color"
    )

    val offset by animateDpAsState(
        targetValue = if (isSwitchOn) {
            45.dp
        } else {
            8.dp
        }, label = "offset", animationSpec = tween(
            300, easing = LinearEasing
        )
    )

    val rotation by animateFloatAsState(
        targetValue = if (isSwitchOn) 180f else 0f,
        animationSpec = tween(300, easing = LinearEasing),
        label = ""
    )

    Box(
        modifier = Modifier
            .padding(20.dp)
            .width(104.dp)
            .height(64.dp)
            .clip(shape = CircleShape)
            .background(bgColor)
            .clickable {
                isSwitchOn = !isSwitchOn
            },
        contentAlignment = Alignment.CenterStart,
    ) {

        Box(
            modifier = Modifier
                .height(50.dp)
                .offset(x = offset)
                .clip(shape = CircleShape)
                .background(thumbColor),
        ) {

            Crossfade(
                isSwitchOn, animationSpec = tween(300), label = ""
            ) { targetState ->

                Icon(
                    painter = painterResource(if (targetState) R.drawable.ic_sun else R.drawable.ic_moon),
                    contentDescription = "Day Mode",
                    modifier = Modifier
                        .padding(10.dp)
                        .rotate(rotation),
                    tint = Color.White
                )
            }
        }
    }
}
