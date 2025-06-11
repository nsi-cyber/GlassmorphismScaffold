package com.nsicyber.glassmorphismscaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
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
import dev.chrisbanes.haze.haze
import dev.chrisbanes.haze.hazeChild
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                 CardBorderEffect()

        }
    }
}

// HazeBase Component - Ana container ve haze state yöneticisi
@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun HazeBase(
    modifier: Modifier = Modifier,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.(HazeState) -> Unit
) {
    val hazeState = remember { HazeState() }

    Box(modifier = modifier) {
        // Haze efekti olan katman (arka plan)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .haze(hazeState)
        ) {


            backgroundContent()

        }

        // Ana içerik katmanı (haze dışında)
        content(hazeState)
    }
}

// HazeChild Component - Blur olan ve olmayan içeriği ayıran
@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
fun HazeChild(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    shape: Shape = RoundedCornerShape(25.dp),
    hazeStyle: HazeStyle = HazeMaterials.ultraThin().copy(
        blurRadius = 10.dp,
        noiseFactor = 0.15f,
        tint = Color.White.copy(alpha = 0.01f)
    ),
    clearContent: @Composable () -> Unit = {}
) {
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    val commonModifier = modifier
        .size(with(LocalDensity.current) { boxSize.toSize().toDpSize() }) // ölçülen boyut
    Box(modifier = modifier) {
// Alt katman: Blur efekti
        Box(
            modifier = commonModifier
                .background(Color.Transparent)
                .hazeChild(
                    state = hazeState,
                    shape = shape,
                    style = hazeStyle
                )
        )


// Üst katman: İçerik
        Box(
            modifier = modifier
                .onGloballyPositioned {
                    boxSize = it.size // ölçü alınıyor
                }
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

    HazeBase(
        modifier = Modifier.fillMaxSize().background(color = Color.Blue),
        backgroundContent = {
            LottieAnimation(
                composition = preloaderLottieComposition,
                progress = preloaderProgress,
                modifier = Modifier.size(200.dp)
            )
        }
    ) { hazeState ->
        LazyRow(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy (20.dp)) { items(8) {
            Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy ( 20.dp )) {
                HazeChild(
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
                HazeChild(
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

        } }

    }
}

