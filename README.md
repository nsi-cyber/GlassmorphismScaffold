# 🌟 GlassmorphicScaffold

[![API](https://img.shields.io/badge/API-24%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=24)
[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.10-blue.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-1.5.4-blue.svg)](https://developer.android.com/jetpack/compose)

A modern Android library that brings iOS-like glassmorphism effects to Jetpack Compose with ease and performance in mind.

## ✨ Features

- 🎨 **iOS-like Glassmorphism**: Create beautiful glass-like blur effects similar to iOS UIVisualEffectView
- ⚡ **Performance Optimized**: GPU-accelerated blur effects using the Haze library
- 🔧 **Highly Customizable**: Fine-tune blur radius, corner radius, noise factor, and colors
- 📱 **Responsive Design**: Automatically adapts to content size
- 🎯 **Easy Integration**: Simple API that works out of the box
- 🔄 **Dynamic Content**: Works with any background content (images, videos, gradients)

## 📱 Demo

https://www.linkedin.com/posts/nsi-cyber_androiddevelopment-ios-jetpackcompose-activity-7338698308711809024-o4QG/

*Beautiful glassmorphism effects running smoothly on Android*

## 🚀 Quick Start

### 1. Add Dependencies

Add to your module's `build.gradle.kts`:

```kotlin
dependencies {
    implementation("dev.chrisbanes.haze:haze-jetpack-compose:0.7.3")
    implementation("dev.chrisbanes.haze:haze-materials:0.7.3")
    implementation("com.google.accompanist:accompanist-drawablepainter:0.32.0")
}
```

### 2. Basic Usage

```kotlin
@Composable
fun MyGlassmorphicScreen() {
    GlassmorphicScaffold(
        backgroundContent = {
            // Any background content (image, video, gradient, etc.)
            Image(
                painter = painterResource(R.drawable.background),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    ) { hazeState ->
        // Your glass content here
        BlurredBox(
            hazeState = hazeState,
            cornerRadius = 16.dp
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Glassmorphism Card",
                    color = Color.White,
                    fontSize = 18.sp
                )
                Text(
                    text = "Beautiful blur effects",
                    color = Color.White.copy(alpha = 0.8f),
                    fontSize = 14.sp
                )
            }
        }
    }
}
```

## 📖 API Reference

### GlassmorphicScaffold

Main container that provides the glassmorphism infrastructure.

```kotlin
@Composable
fun GlassmorphicScaffold(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Blue,
    backgroundContent: @Composable BoxScope.() -> Unit = {},
    content: @Composable BoxScope.(HazeState) -> Unit
)
```

**Parameters:**
- `modifier`: Modifier for the root container
- `backgroundColor`: Background color (default: Blue)
- `backgroundContent`: Content that serves as the blur source
- `content`: Content that receives HazeState for blur effects

### BlurredBox

Customizable blurred container for glassmorphism effects.

```kotlin
@Composable
fun BlurredBox(
    modifier: Modifier = Modifier,
    hazeState: HazeState,
    noiseFactor: Float = 0.15f,
    blurRadius: Dp = 10.dp,
    cornerRadius: Dp = 25.dp,
    blurColor: Color = Color.White.copy(alpha = 0.01f),
    hazeStyle: HazeStyle = HazeMaterials.ultraThin().copy(...),
    clearContent: @Composable () -> Unit = {}
)
```

**Parameters:**
- `hazeState`: HazeState from GlassmorphicScaffold
- `noiseFactor`: Noise amount (0.0 - 1.0)
- `blurRadius`: Blur effect radius
- `cornerRadius`: Corner radius for rounded corners
- `blurColor`: Tint color for the blur
- `hazeStyle`: Custom HazeStyle for advanced configuration
- `clearContent`: Content inside the blurred container

## 🎨 Customization Examples

### Media Player Card

```kotlin
@Composable
fun MediaPlayerCard() {
    GlassmorphicScaffold(
        backgroundContent = {
            AsyncImage(
                model = "https://example.com/album-cover.jpg",
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
    ) { hazeState ->
        BlurredBox(
            hazeState = hazeState,
            cornerRadius = 20.dp,
            noiseFactor = 0.05f,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { /* Play */ }) {
                    Icon(
                        Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White
                    )
                }
                // More controls...
            }
        }
    }
}
```

### Settings Panel

```kotlin
@Composable
fun SettingsPanel() {
    GlassmorphicScaffold(
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF1e3c72),
                                Color(0xFF2a5298)
                            )
                        )
                    )
            )
        }
    ) { hazeState ->
        BlurredBox(
            hazeState = hazeState,
            cornerRadius = 12.dp,
            blurRadius = 15.dp,
            noiseFactor = 0.1f
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White
                )
                // Settings items...
            }
        }
    }
}
```

## ⚡ Performance Tips

1. **Background Content**: Use optimized images and avoid complex animations in background
2. **Blur Radius**: Lower values (5-15dp) provide better performance
3. **Noise Factor**: Values between 0.05-0.15 offer good balance
4. **Content Size**: Larger blurred areas may impact performance on older devices

## 🔧 Advanced Configuration

### Custom HazeStyle

```kotlin
val customHazeStyle = HazeMaterials.ultraThin().copy(
    blurRadius = 20.dp,
    noiseFactor = 0.08f,
    backgroundColor = Color.Transparent,
    tints = listOf(
        HazeTint(color = Color.Blue.copy(alpha = 0.1f)),
        HazeTint(color = Color.White.copy(alpha = 0.05f))
    )
)

BlurredBox(
    hazeState = hazeState,
    hazeStyle = customHazeStyle
) {
    // Content
}
```

## 📋 Requirements

- **Android API Level**: 24+ (Android 7.0)
- **Jetpack Compose**: 1.5.0+
- **Kotlin**: 1.9.0+

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🙏 Acknowledgments

- [Haze](https://github.com/chrisbanes/haze) - The amazing blur library that powers this project
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern toolkit for native UI
- iOS UIVisualEffectView - Inspiration for the API design


---

⭐ If this project helped you, please give it a star! 
