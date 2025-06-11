package com.nsicyber.glassmorphismscaffold

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CardBorderEffect()

        }
    }
}

@Composable
fun GlassmorphicScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.(HazeState) -> Unit
) {
    val hazeState = rememberHazeState()

    Box(modifier = modifier) {
        // Haze efekti olan katman (arka plan)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState)
                .background(backgroundColor)
        ) {


            backgroundContent()

        }

        // Ana içerik katmanı (haze dışında)
        content(hazeState)
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun BlurredBox(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    noiseFactor: Float = 0.15f,
    blurRadius: Dp = 10.dp,
    cornerRadius:Dp =25.dp,
    blurColor: Color = Color.White.copy(alpha = 0.01f),
    hazeStyle: HazeStyle = HazeMaterials.ultraThin().copy(
        blurRadius = blurRadius,
        noiseFactor = noiseFactor,
        backgroundColor = Color.Transparent,
        tints = listOf(HazeTint(color = blurColor)),
        fallbackTint = HazeTint(color = blurColor)
    ),
    clearContent: @Composable () -> Unit = {}
) {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }
    val gradientBorderBrush = Brush.linearGradient(
        colors = listOf(
            Color.White.copy(alpha = 0.4f), // üst sol – ışık vuruyor gibi
            Color.White.copy(alpha = 0.1f), // alt sağ – daha az yansıma
        ),
        start = Offset.Zero,
        end = Offset.Infinite
    )
    val commonModifier = modifier
        .size(with(LocalDensity.current) { boxSize.toSize().toDpSize() }) // ölçülen boyut
    Box(modifier = modifier .clip(RoundedCornerShape(cornerRadius))) {
// Alt katman: Blur efekti
        Box(
            modifier = commonModifier.background(
                // Glass material overlay
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.White.copy(alpha = 0.15f),
                        Color.White.copy(alpha = 0.07f),
                        Color.White.copy(alpha = 0.02f)
                    ),
                    start = Offset(0f, 0f),
                    end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                ),
                shape = RoundedCornerShape(cornerRadius)
            )
                .border(
                    width = 1.5.dp,
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.15f),
                            Color.White.copy(alpha = 0.07f),
                            Color.White.copy(alpha = 0.02f)
                        )
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .hazeEffect(
                    state = hazeState,
                    style = hazeStyle,
                )
        )


// Üst katman: İçerik
        Box(
            modifier = modifier
                .onGloballyPositioned {
                    boxSize = it.size // ölçü alınıyor
                }
                ,
        ) {
            clearContent()
        }
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalComposeUiApi::class)
@Composable
fun CardBorderEffect() {
    val loremTexts = listOf(
        "Lorem ipsum dolor sit ametLorem ipsum dolor sit amet",
        "consectetur adipiscingconsectetur adipiscing elit",
        "sed do eiusmodsed do eiusmod tempor",
        "incididunt ut laboreincididunt ut labore et",
        "dolore magnadolore magna aliqua",
        "Ut enim ad minimad minim veniam",
        "quis nostrud exercitationnostrud exercitation",
        "ullamco laborisullamco laboris nisi ut",
        "aliquip ex ea commodoex ea commodo",
        "consequat duisconsequat duis aute irure",
        "dolor in reprehenderitin reprehenderit",
        "in voluptatevoluptate velit esse",
        "cillum dolorecillum dolore eu fugiat",
        "nulla pariaturnulla pariatur excepteur",
        "sint occaecatoccaecat cupidatat",
        "non proident suntproident sunt in culpa"
    )

    val preloaderLottieComposition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(
            R.raw.lottie
        )
    )

    val preloaderProgress by animateLottieCompositionAsState(
        preloaderLottieComposition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true
    )

    GlassmorphicScaffold(
        backgroundColor = Color.Blue,
        modifier = Modifier.fillMaxSize(),
        backgroundContent = {


            LottieAnimation(
                composition = preloaderLottieComposition,
                progress = preloaderProgress,
                modifier = Modifier.fillMaxSize()
            )
            Text(text = loremTexts.toString())


        }
    ) { hazeState ->
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            items(8) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    repeat(6) {
                        BlurredBox(
                            hazeState = hazeState,
                            clearContent = {
                                Column {

                                    Text(
                                        text = "Glassmorphism\nEffect",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        textAlign = TextAlign.Center,
                                        modifier = Modifier
                                            .padding(16.dp)
                                    )


                                }

                            }
                        )
                    }


                }

            }
        }

    }
}

