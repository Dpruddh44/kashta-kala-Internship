package com.kalakashta.app.feature.inspiration

import android.content.res.Configuration
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.staggeredgrid.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.kalakashta.app.core.designsystem.Artisan
import com.kalakashta.app.core.designsystem.ArtisanTheme
import com.kalakashta.app.core.widget.ShimmerBox
import com.kalakashta.app.data.FurnitureFamily
import com.kalakashta.app.data.InspirationItem

// ═══════════════════════════════════════════════════════════════
// INSPIRATION SCREEN — The Visual Hook
// ═══════════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InspirationScreen(
    state: InspirationState,
    onFamilyPick: (FurnitureFamily) -> Unit,
    onSearchChange: (String) -> Unit,
    onSearchSubmit: () -> Unit,
    onHeartTap: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = Artisan.palette
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = colors.canvas,
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            "Inspiration",
                            style = MaterialTheme.typography.displaySmall,
                            fontWeight = FontWeight.Bold,
                            color = colors.inkWhite
                        )
                        Text(
                            "Discover modern designs",
                            style = MaterialTheme.typography.bodySmall,
                            color = colors.inkAsh
                        )
                    }
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = colors.canvas,
                    scrolledContainerColor = colors.surface,
                ),
                scrollBehavior = scrollBehavior,
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // ── Search Bar ──
            SearchRow(
                query = state.searchQuery,
                onQueryChange = onSearchChange,
                onSubmit = onSearchSubmit,
            )

            Spacer(Modifier.height(12.dp))

            // ── Filter Chips ──
            FamilyChipRow(
                active = state.activeFamily,
                onPick = onFamilyPick,
            )

            Spacer(Modifier.height(16.dp))

            // ── Error Banner ──
            state.error?.let { msg ->
                Text(
                    text = msg,
                    style = MaterialTheme.typography.bodySmall,
                    color = colors.marigold,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 4.dp)
                )
            }

            // ── Staggered Grid ──
            if (state.loading) {
                LoadingGrid()
            } else {
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(2),
                    contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    verticalItemSpacing = 12.dp,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                ) {
                    items(state.items, key = { it.uid }) { item ->
                        InspirationCard(
                            item = item,
                            onHeartTap = { onHeartTap(item.uid) }
                        )
                    }
                }
            }
        }
    }
}

// ── Search Row ──
@Composable
private fun SearchRow(
    query: String,
    onQueryChange: (String) -> Unit,
    onSubmit: () -> Unit,
) {
    val colors = Artisan.palette
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .height(50.dp)
            .clip(RoundedCornerShape(25.dp))
            .background(colors.card)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            Icons.Rounded.Search,
            contentDescription = "Search",
            tint = colors.inkAsh,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(12.dp))
        BasicTextField(
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.inkWhite),
            cursorBrush = SolidColor(colors.spark),
            modifier = Modifier.weight(1f),
            decorationBox = { inner ->
                Box(contentAlignment = Alignment.CenterStart) {
                    if (query.isEmpty()) {
                        Text("Search furniture designs…", color = colors.inkAsh,
                            style = MaterialTheme.typography.bodyMedium)
                    }
                    inner()
                }
            }
        )
        if (query.isNotBlank()) {
            TextButton(onClick = onSubmit) {
                Text("Go", color = colors.spark, fontWeight = FontWeight.Bold)
            }
        }
    }
}

// ── Filter Chip Row ──
@Composable
private fun FamilyChipRow(
    active: FurnitureFamily,
    onPick: (FurnitureFamily) -> Unit,
) {
    val colors = Artisan.palette
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(FurnitureFamily.entries) { family ->
            val selected = family == active
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = if (selected) colors.spark else colors.card,
                contentColor = if (selected) colors.canvas else colors.inkSilver,
                modifier = Modifier.clickable { onPick(family) }
            ) {
                Text(
                    text = family.label,
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 10.dp)
                )
            }
        }
    }
}

// ── Single Inspiration Card ──
@Composable
private fun InspirationCard(
    item: InspirationItem,
    onHeartTap: () -> Unit,
) {
    val colors = Artisan.palette

    // Heart bounce animation
    val heartScale by animateFloatAsState(
        targetValue = if (item.hearted) 1f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "heart"
    )
    var heartBounce by remember { mutableStateOf(false) }
    val bounceScale by animateFloatAsState(
        targetValue = if (heartBounce) 1.35f else 1f,
        animationSpec = spring(dampingRatio = 0.35f, stiffness = 600f),
        finishedListener = { heartBounce = false },
        label = "bounce"
    )

    val cardHeight = (180 * item.ratio).dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(cardHeight)
            .clip(RoundedCornerShape(16.dp))
    ) {
        // Image with shimmer loading
        SubcomposeAsyncImage(
            model = item.thumbUrl,
            contentDescription = item.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            loading = {
                ShimmerBox(modifier = Modifier.fillMaxSize(), cornerRadius = 16.dp)
            },
            error = {
                Box(
                    Modifier.fillMaxSize().background(colors.card),
                    contentAlignment = Alignment.Center
                ) {
                    Text("⚒", style = MaterialTheme.typography.displaySmall)
                }
            }
        )

        // Bottom gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                    )
                )
        )

        // Title
        Text(
            text = item.title,
            style = MaterialTheme.typography.labelLarge,
            color = Color.White,
            maxLines = 2,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp)
        )

        // Heart button
        Box(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(8.dp)
                .size(36.dp)
                .clip(CircleShape)
                .background(Color.Black.copy(alpha = 0.4f))
                .clickable {
                    heartBounce = true
                    onHeartTap()
                }
                .scale(bounceScale),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (item.hearted) Icons.Rounded.Favorite
                else Icons.Rounded.FavoriteBorder,
                contentDescription = "Favorite",
                tint = if (item.hearted) colors.ember else Color.White,
                modifier = Modifier.size(20.dp)
            )
        }

        // Artist credit
        if (item.artist.isNotBlank()) {
            Text(
                text = "📷 ${item.artist}",
                style = MaterialTheme.typography.labelSmall,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(8.dp)
            )
        }
    }
}

// ── Loading Shimmer Grid ──
@Composable
private fun LoadingGrid() {
    LazyVerticalStaggeredGrid(
        columns = StaggeredGridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
        verticalItemSpacing = 12.dp,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(8) { idx ->
            ShimmerBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(listOf(200, 260, 180, 240, 220, 280, 190, 250)[idx].dp)
            )
        }
    }
}

// ═══════════════════════════════════════════════════════════════
// PREVIEW
// ═══════════════════════════════════════════════════════════════

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun InspirationPreview() {
    val mockItems = listOf(
        InspirationItem("1", "Modern Oak Sofa", FurnitureFamily.SOFA,
            "https://source.unsplash.com/featured/?sofa",
            "https://source.unsplash.com/featured/?sofa",
            "John Doe", false, 1.2f),
        InspirationItem("2", "Teak Dining Table", FurnitureFamily.TABLE,
            "https://source.unsplash.com/featured/?table",
            "https://source.unsplash.com/featured/?table",
            "Jane Smith", true, 0.9f),
        InspirationItem("3", "Walnut Bookshelf", FurnitureFamily.SHELF,
            "https://source.unsplash.com/featured/?bookshelf",
            "https://source.unsplash.com/featured/?bookshelf",
            "Artisan", false, 1.5f),
        InspirationItem("4", "Minimalist Bed Frame", FurnitureFamily.BED,
            "https://source.unsplash.com/featured/?bed",
            "https://source.unsplash.com/featured/?bed",
            "Studio K", true, 1.1f),
    )
    ArtisanTheme {
        InspirationScreen(
            state = InspirationState(
                items = mockItems,
                loading = false,
                heartedIds = setOf("2", "4")
            ),
            onFamilyPick = {},
            onSearchChange = {},
            onSearchSubmit = {},
            onHeartTap = {},
        )
    }
}
