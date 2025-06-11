package com.nsicyber.glassmorphismscaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.compose.foundation.Image
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dev.chrisbanes.haze.ExperimentalHazeApi
import dev.chrisbanes.haze.HazeState
import dev.chrisbanes.haze.HazeStyle
import dev.chrisbanes.haze.HazeTint
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState

/**
 * MainActivity demonstrating the GlassmorphicScaffold component
 * 
 * This activity showcases how to create iOS-like glassmorphism effects
 * in Android using Jetpack Compose and the Haze library.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GlassmorphismDemo()
        }
    }
}



/**
 * Demo function showcasing the glassmorphism components in action
 * 
 * Creates a space-themed background with multiple blurred cards arranged
 * in a scrollable row layout, demonstrating various use cases for the
 * glassmorphism effects.
 */
@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalComposeUiApi::class)
@Composable
fun GlassmorphismDemo() {
    GlassmorphicScaffold(
        modifier = Modifier.fillMaxSize(),
        backgroundContent = {
            // Space background image as blur source
            Image(
                modifier = Modifier.fillMaxSize(),
                painter = rememberDrawablePainter(
                    drawable = getDrawable(
                        LocalContext.current,
                        R.drawable.space
                    )
                ),
                contentDescription = "Space background for glassmorphism demo",
                contentScale = ContentScale.Crop,
            )
        }
    ) { hazeState ->
        // Horizontal scrollable list of glassmorphic cards
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(50.dp)
        ) {
            items(8) { index ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(50.dp)
                ) {
                    repeat(2) { cardIndex ->
                        // Demonstrate different glassmorphism styles
                        when (cardIndex) {
                            0 -> {
                                // Glassmorphic card with ultra-thin preset
                                BlurredBox(
                                    hazeState = hazeState,
                                    style = GlassmorphismPresets.ultraThin
                                ) {
                                    DemoCardContent(
                                        title = "Ultra Thin",
                                        subtitle = "Minimal blur effect"
                                    )
                                }
                            }
                            1 -> {
                                // Glassmorphic card with regular preset
                                BlurredBox(
                                    hazeState = hazeState,
                                    style = GlassmorphismPresets.regular
                                ) {
                                    DemoCardContent(
                                        title = "Regular",
                                        subtitle = "Balanced blur effect"
                                    )
                                }
                            }
                        }

                        // Alternative card layout without blur background
                        // Shows content that doesn't need glassmorphism effect
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Image(
                                painter = painterResource(R.drawable.playstore),
                                contentDescription = "App icon",
                                modifier = Modifier.size(40.dp)
                            )

                            Text(
                                text = "Regular Card",
                                color = Color.White,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

/**
 * Reusable content component for demo cards
 * 
 * @param title Main title text
 * @param subtitle Secondary subtitle text
 */
@Composable
private fun DemoCardContent(
    title: String,
    subtitle: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // App icon
        Image(
            painter = painterResource(R.drawable.playstore),
            contentDescription = "App icon",
            modifier = Modifier.size(40.dp)
        )

        // Title
        Text(
            text = title,
            color = Color.White,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        
        // Subtitle
        Text(
            text = subtitle,
            color = Color.White.copy(alpha = 0.7f),
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
        )
    }
}

