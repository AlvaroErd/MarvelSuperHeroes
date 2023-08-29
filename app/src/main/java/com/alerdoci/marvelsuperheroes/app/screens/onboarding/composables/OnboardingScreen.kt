package com.alerdoci.marvelsuperheroes.app.screens.onboarding.composables

import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.alerdoci.marvelsuperheroes.R
import com.alerdoci.marvelsuperheroes.app.common.utils.Constants.LOREM_IPSUM_SHORT
import com.alerdoci.marvelsuperheroes.app.components.DefaultButton
import com.alerdoci.marvelsuperheroes.app.navigation.Screen
import com.alerdoci.marvelsuperheroes.app.screens.onboarding.viewmodel.OnboardingViewModel
import com.alerdoci.marvelsuperheroes.app.theme.spacing
import com.alerdoci.marvelsuperheroes.app.theme.white
import tech.devscion.typist.Typist
import tech.devscion.typist.TypistSpeed

@Composable
fun OnBoardingScreen(
    navController: NavHostController
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        OnBoardingContent(navController = navController)
    }
}

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
                .fillMaxHeight(1f)
                .fillMaxWidth(1f)
                .blur(2.dp, 2.dp),
            painter = painterResource(id = R.drawable.bg_planet),
            contentDescription = "Marvel Superheroes",
            contentScale = ContentScale.Crop
        )
        Image(
            modifier = Modifier
                .padding(top = MaterialTheme.spacing.extraLarge)
                .fillMaxHeight(0.55f)
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    delayMillis = 0,
                    velocity = 8.dp,
                    initialDelayMillis = 0
                )
                .offset(50.dp, 0.dp),
            painter = painterResource(id = R.drawable.marvel_superheroes_onboarding),
            contentDescription = "Marvel Superheroes",
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .padding(bottom = MaterialTheme.spacing.tiny),
            verticalArrangement = Arrangement.Bottom
        ) {
            val offset = Offset(10.0f, 15.0f)

            Typist(
                text = stringResource(R.string.welcome_to),
                modifier = Modifier.fillMaxWidth(),
                typistSpeed = TypistSpeed.NORMAL,
                textStyle = MaterialTheme.typography.displayLarge.copy(
                    color = white, fontSize = 36.sp,
                    shadow = Shadow(
                        color = Color.Black, offset = offset, blurRadius = 10f
                    )
                ),
                isBlinkingCursor = true,
                isInfiniteCursor = false,
                isCursorVisible = false,
            )

            Typist(
                text = stringResource(R.string.marvel_universe),
                modifier = Modifier.fillMaxWidth(),
                typistSpeed = TypistSpeed.FAST,
                textStyle = MaterialTheme.typography.displayLarge.copy(
                    color = white, fontSize = 42.sp,
                    shadow = Shadow(
                        color = Color.Black, offset = offset, blurRadius = 10f
                    )
                ),
                isBlinkingCursor = true,
                isInfiniteCursor = false,
                isCursorVisible = false,
            )

            Spacer(Modifier.height(MaterialTheme.spacing.large))

            Text(
                text = LOREM_IPSUM_SHORT,
                style = MaterialTheme.typography.titleLarge.copy(
                    shadow = Shadow(
                        color = Color.Black, offset = offset, blurRadius = 10f
                    )
                ),
                color = Color.White,

                )
            Spacer(Modifier.height(MaterialTheme.spacing.extraLarge))

            DefaultButton(
                text = "Get Started",
                containerColor = Color.White,
                contentColor = Color.Black,
            ) {
                onboardingViewModel.saveOnBoardingState(completed = true)
                navController.popBackStack()
                navController.navigate(Screen.Home.route)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewOnBoardingScreen() {
    val navController = rememberNavController()
    OnBoardingScreen(navController)
}
