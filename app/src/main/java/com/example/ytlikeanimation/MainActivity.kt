package com.example.ytlikeanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.Group
import androidx.compose.ui.graphics.vector.Path
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ytlikeanimation.ui.theme.*
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YTLikeAnimationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    YtLikeAnimation()
                }
            }
        }
    }

    @Preview
    @Composable
    fun YtLikeAnimation() {
        val backHandPath = PathParser().parsePathString(
            "M7.66,10.102L11.76,4.002C12.16,3.402 13.16,3.002 13.96,3.302C14.86,3.602 15.46,4.602 15.26,5.502L14.76,8.702C14.66,9.402 15.16,9.902 15.76,9.902H19.76C21.26,9.902 22.18,11.052 21.66,12.502C21.14,13.952 20.68,16.552 19.26,18.802C18.61,19.832 17.897,20.552 16.68,20.552C12.68,20.552 6.66,20.552 7.66,20.552"
        ).toNodes()

        val forehandPath = PathParser().parsePathString(
            "M2.18,10.552C2.18,10 2.628,9.552 3.18,9.552H6.68C7.232,9.552 7.68,10 7.68,10.552V20.552H3.18C2.628,20.552 2.18,20.104 2.18,19.552V10.552Z"
        ).toNodes()
        var isLiked by remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()
        val fillColor = remember {
            Animatable(Color.White)
        }

        val strokeColor = remember {
            Animatable(Color.Black)
        }
        val angle = remember {
            Animatable(0f)
        }
        val confettiSize = remember {
            Animatable(24f)
        }
        val confettiAlpha = remember {
            Animatable(0f)
        }
        val vectorPainter = rememberVectorPainter(
            defaultWidth = 30.dp,
            defaultHeight = 30.dp,
            viewportWidth = 28f,
            viewportHeight = 28f,
            autoMirror = true,
        ) { _, _ ->
            Group(
                name = "likebutton"
            ) {
                Group("backhand", translationX = 2f) {
                    Path(
                        pathData = backHandPath,
                        fill = SolidColor(fillColor.value),
                        stroke = SolidColor(strokeColor.value),
                        strokeLineWidth = 1.5f
                    )
                }

                Group("forehand") {
                    Path(
                        pathData = forehandPath,
                        fill = SolidColor(fillColor.value),
                        stroke = SolidColor(strokeColor.value),
                        strokeLineWidth = 1.5f
                    )
                }
            }
        }

        Box {
            Image(
                painter = painterResource(id = R.drawable.cnfetti_icon),
                contentDescription = "Confetti",
                modifier = Modifier
                    .align(Alignment.Center)
                    .alpha(confettiAlpha.value)
                    .size(confettiSize.value.dp)

            )
            IconButton(modifier = Modifier.align(Alignment.Center), onClick = {
                if (!isLiked) {
                    scope.launch {
                        launch {
                            fillColor.animateTo(Zinc, tween(150, easing = LinearEasing))
                            fillColor.animateTo(Blue, tween(150, easing = LinearEasing))
                            fillColor.animateTo(Purple, tween(150, easing = LinearEasing))
                            fillColor.animateTo(Mustard, tween(150, easing = LinearEasing))
                            fillColor.animateTo(Yellow, tween(150, easing = LinearEasing))
                            fillColor.animateTo(Orange, tween(150, easing = LinearEasing))
                            fillColor.animateTo(Red, tween(150, easing = LinearEasing))
                            fillColor.animateTo(Color.Black, tween(200, easing = EaseOutQuart))
                        }
                        launch {
                            strokeColor.animateTo(Zinc, tween(150, easing = LinearEasing))
                            strokeColor.animateTo(Blue, tween(150, easing = LinearEasing))
                            strokeColor.animateTo(Purple, tween(150, easing = LinearEasing))
                            strokeColor.animateTo(Mustard, tween(150, easing = LinearEasing))
                            strokeColor.animateTo(Yellow, tween(150, easing = LinearEasing))
                            strokeColor.animateTo(Orange, tween(150, easing = LinearEasing))
                            strokeColor.animateTo(Red, tween(150, easing = LinearEasing))
                            strokeColor.animateTo(Color.Black, tween(200, easing = EaseOutQuart))
                        }
                        launch {
                            angle.animateTo(-45f, tween(550, easing = EaseOutQuart))
                            angle.animateTo(0f, tween(600, easing = EaseOutBack))
                        }
                        launch {
                            confettiSize.animateTo(50f, tween(450))
                            confettiSize.animateTo(24f, tween(450))
                        }
                        launch {
                            confettiAlpha.animateTo(1f, tween(450))
                            confettiAlpha.animateTo(0f, tween(100))
                        }
                    }
                } else {
                    scope.launch {
                        fillColor.animateTo(Color.White, tween(100))
                        strokeColor.animateTo(Color.Black, tween(100))
                        angle.animateTo(0f, tween(100))
                    }
                }
                isLiked = !isLiked
            }) {
                Image(
                    painter = vectorPainter, contentDescription = "Like Button", modifier = Modifier
                        .size(24.dp)
                        .rotate(angle.value)
                )
            }
        }
    }
}