package com.nsicyber.glassmorphismscaffold

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
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
 * A scaffold component that provides glassmorphism effects for Android apps
 * 
 * This component creates a layered structure where background content serves as
 * the blur source, and foreground content can apply blur effects using the provided HazeState.
 * 
 * Similar to iOS's UIVisualEffectView, this provides an easy way to implement
 * glassmorphism in Jetpack Compose applications.
 *
 * @param modifier Modifier to be applied to the root container
 * @param backgroundColor Background color for the container (default: Blue)
 * @param backgroundContent Composable content that serves as the blur source
 * @param content Composable content that receives HazeState for applying blur effects
 * 
 * @sample
 * ```
 * GlassmorphicScaffold(
 *     backgroundContent = {
 *         Image(painter = painterResource(R.drawable.background), ...)
 *     }
 * ) { hazeState ->
 *     BlurredBox(hazeState = hazeState) {
 *         Text("Glassmorphic Content")
 *     }
 * }
 * ```
 */
@Composable
fun GlassmorphicScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.(HazeState) -> Unit
) {
    // Create and remember the HazeState for blur operations
    val hazeState = rememberHazeState()

    Box(modifier = modifier) {
        // Background layer: Source for blur effects
        Box(
            modifier = Modifier
                .fillMaxSize()
                .hazeSource(hazeState) // Mark this as the source for blur operations
                .background(backgroundColor)
        ) {
            backgroundContent()
        }
        
        // Foreground layer: Content that can apply blur effects
        content(hazeState)
    }
}

/**
 * A customizable blurred container component for glassmorphism effects
 * 
 * This component creates a glass-like appearance with blur effects, gradient overlays,
 * and subtle borders. It automatically adapts its size based on the content dimensions.
 *
 * @param modifier Modifier to be applied to the component
 * @param hazeState HazeState from GlassmorphicScaffold for blur operations
 * @param noiseFactor Amount of noise to add to the blur effect (0.0 - 1.0)
 * @param blurRadius Radius of the blur effect in Dp
 * @param cornerRadius Corner radius for rounded corners
 * @param blurColor Tint color applied to the blur effect
 * @param hazeStyle Custom HazeStyle for advanced blur configuration
 * @param clearContent Content to be displayed inside the blurred container
 * 
 * @sample
 * ```
 * BlurredBox(
 *     hazeState = hazeState,
 *     cornerRadius = 16.dp,
 *     noiseFactor = 0.1f
 * ) {
 *     Column {
 *         Text("Title", color = Color.White)
 *         Text("Subtitle", color = Color.White.copy(alpha = 0.7f))
 *     }
 * }
 * ```
 */
@OptIn(ExperimentalHazeMaterialsApi::class, ExperimentalHazeApi::class)
@Composable
fun BlurredBox(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    noiseFactor: Float = 0.15f,
    blurRadius: Dp = 10.dp,
    cornerRadius: Dp = 25.dp,
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
    // State to track the measured size of the content
    var boxSize by remember { mutableStateOf(IntSize.Zero) }

    // Calculate modifier with dynamic size based on measured content
    val commonModifier = modifier
        .size(with(LocalDensity.current) { boxSize.toSize().toDpSize() })
    
    // Main container with clipped corners
    Box(modifier = modifier.clip(RoundedCornerShape(cornerRadius))) {
        
        // Background layer: Blur effect with glass material styling
        Box(
            modifier = commonModifier
                .background(
                    // Glass material overlay gradient
                    // Mimics the subtle highlights and shadows of real glass
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.15f), // Top highlight
                            Color.White.copy(alpha = 0.07f), // Middle transition
                            Color.White.copy(alpha = 0.02f)  // Bottom shadow
                        ),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    ),
                    shape = RoundedCornerShape(cornerRadius)
                )
                .border(
                    // Subtle border for glass edge definition
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

        // Foreground layer: Clear content with size measurement
        Box(
            modifier = modifier
                .onGloballyPositioned { coordinates ->
                    // Measure the actual size of the content for dynamic sizing
                    boxSize = coordinates.size
                },
        ) {
            clearContent()
        }
    }
}

/**
 * Preset configurations for different glassmorphism styles
 */
object GlassmorphismPresets {
    
    /**
     * Ultra-thin glass effect with minimal blur
     * Perfect for subtle overlays and notifications
     */
    @OptIn(ExperimentalHazeMaterialsApi::class)
    val ultraThin: GlassmorphismStyle = GlassmorphismStyle(
        blurRadius = 8.dp,
        noiseFactor = 0.05f,
        cornerRadius = 12.dp,
        blurColor = Color.White.copy(alpha = 0.01f)
    )
    
    /**
     * Regular glass effect with moderate blur
     * Ideal for cards and content containers
     */
    @OptIn(ExperimentalHazeMaterialsApi::class)
    val regular: GlassmorphismStyle = GlassmorphismStyle(
        blurRadius = 15.dp,
        noiseFactor = 0.1f,
        cornerRadius = 16.dp,
        blurColor = Color.White.copy(alpha = 0.03f)
    )
    
    /**
     * Thick glass effect with heavy blur
     * Best for prominent UI elements and modals
     */
    @OptIn(ExperimentalHazeMaterialsApi::class)
    val thick: GlassmorphismStyle = GlassmorphismStyle(
        blurRadius = 25.dp,
        noiseFactor = 0.15f,
        cornerRadius = 20.dp,
        blurColor = Color.White.copy(alpha = 0.05f)
    )
}

/**
 * Data class representing a glassmorphism style configuration
 */
data class GlassmorphismStyle(
    val blurRadius: Dp,
    val noiseFactor: Float,
    val cornerRadius: Dp,
    val blurColor: Color
)

/**
 * Extension function to apply a glassmorphism preset to BlurredBox
 */
@Composable
fun BlurredBox(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    style: GlassmorphismStyle,
    clearContent: @Composable () -> Unit = {}
) {
    BlurredBox(
        modifier = modifier,
        hazeState = hazeState,
        noiseFactor = style.noiseFactor,
        blurRadius = style.blurRadius,
        cornerRadius = style.cornerRadius,
        blurColor = style.blurColor,
        clearContent = clearContent
    )
} 