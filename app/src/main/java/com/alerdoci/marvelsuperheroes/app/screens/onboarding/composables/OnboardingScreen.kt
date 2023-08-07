package com.alerdoci.marvelsuperheroes.app.screens.onboarding.composables

import android.annotation.SuppressLint
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.utils.Constants.LOREM_IPSUM_SHORT
import com.alerdoci.marvelsuperheroes.app.components.DefaultButton
import com.alerdoci.marvelsuperheroes.app.navigation.Screen
import com.alerdoci.marvelsuperheroes.app.screens.onboarding.viewmodel.OnboardingViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun OnBoardingScreen(
    navController: NavHostController
) {
    val systemUiControl = rememberSystemUiController()

    systemUiControl.setStatusBarColor(
        color = MaterialTheme.colorScheme.background,
        darkIcons = false
    )

    Surface(modifier = Modifier.fillMaxSize()) {
        OnBoardingContent(navController = navController)
    }
}

@SuppressLint("Range")
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingContent(
    onboardingViewModel: OnboardingViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.TopCenter
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(0.9f)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    velocity = 20.dp,
                    initialDelayMillis = 0
                ),
            painter = painterResource(id = R.drawable.marvel_superheroes_onboarding),
            contentDescription = "Marvel Superheroes",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .aspectRatio(2 / 4f),
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = "Wellcome to Marvel Universe",
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.displayLarge,
            )
            Text(
                text = LOREM_IPSUM_SHORT,
                style = MaterialTheme.typography.titleLarge,

                )
            DefaultButton(
                text = "Get Started",
                containerColor = Color.White,
                contentColor = Color.Black,
            ) {
                onboardingViewModel.saveOnBoardingState(completed = true)
                navController.navigate(Screen.Home.route)
            }
        }
    }
}

//
//
//@Preview(showSystemUi = true)
//@Composable
//fun PreviewOnBoardingScreen() {
//    OnBoardingScreen()
//}