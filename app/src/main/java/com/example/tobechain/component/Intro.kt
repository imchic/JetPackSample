package com.example.tobechain.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import com.airbnb.lottie.compose.*
import com.example.tobechain.R

@Composable
fun showIntroLottieAnimation() {

    // lottie animation file from https://lottiefiles.com/1043-loading (by @mohamed_ayman)

    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.popup_builder))
    Box(
        // center
        modifier = Modifier.fillMaxSize(0.5f).fillMaxHeight(0.5f),
        content = {
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f),
                contentScale = ContentScale.FillBounds,
            )
        }
    )

}