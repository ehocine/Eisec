package com.helic.eisec.view.animationviewstate

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.helic.eisec.ui.theme.AppTheme
import com.helic.eisec.R


@Composable
fun AnimationViewState(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    callToAction: String,
    screenState: ScreenState,
    actions: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
            .wrapContentSize(Alignment.Center),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        when (screenState) {
            ScreenState.ERROR -> {
                LottieAnimationPlaceHolder(
                    modifier,
                    title,
                    description,
                    callToAction,
                    actions,
                    R.raw.error_state
                )
            }
            ScreenState.EMPTY -> {
                LottieAnimationPlaceHolder(
                    modifier,
                    title,
                    description,
                    callToAction,
                    actions,
                    R.raw.empty_state
                )
            }
            ScreenState.LOADING -> {
                LottieAnimation(modifier, lottie = R.raw.loading_state)
            }
        }
    }
}

@Composable
fun LottieAnimationPlaceHolder(
    modifier: Modifier,
    title: String,
    description: String,
    callToAction: String,
    actions: () -> Unit,
    lottie: Int,
) {

    // lottie animation
    LottieAnimation(modifier, lottie)

    // title, description & CTA button
    Text(
        text = title,
        modifier = modifier.fillMaxWidth(),
        style = AppTheme.typography.h2,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.text
    )
    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingMedium))
    Text(
        text = description,
        modifier = modifier
            .fillMaxWidth()
            .padding(
                start = AppTheme.dimensions.paddingXXL,
                end = AppTheme.dimensions.paddingXXL
            ),
        style = AppTheme.typography.body,
        maxLines = 3,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.text.copy(.7F)
    )
    Spacer(modifier = modifier.height(AppTheme.dimensions.paddingXXL))
    Button(
        onClick = { actions() },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.white
        )
    ) {
        Text(text = callToAction, style = AppTheme.typography.button)
    }
}

@Composable
fun LottieAnimation(modifier: Modifier, lottie: Int) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(lottie))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever,
    )
    com.airbnb.lottie.compose.LottieAnimation(
        modifier = modifier.size(300.dp),
        composition = composition,
        progress = progress
    )
}

enum class ScreenState {
    ERROR,
    EMPTY,
    LOADING
}