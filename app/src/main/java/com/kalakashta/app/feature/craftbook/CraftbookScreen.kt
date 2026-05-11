package com.kalakashta.app.feature.craftbook

import android.content.res.Configuration
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.kalakashta.app.core.designsystem.Artisan
import com.kalakashta.app.core.designsystem.ArtisanTheme
import com.kalakashta.app.core.widget.PolaroidFrame
import com.kalakashta.app.core.widget.ShimmerBox
import com.kalakashta.app.persist.CraftRecord

@Composable
fun CraftbookScreen(
    state: CraftbookState,
    onToggleDialog: () -> Unit,
    onCaptionChange: (String) -> Unit,
    onPhotoSelected: (String) -> Unit,
    onRemove: (CraftRecord) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = Artisan.palette
    val imagePicker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onPhotoSelected(it.toString()) }
    }

    Box(modifier = modifier.fillMaxSize().background(colors.canvas)) {
        Column(modifier = Modifier.fillMaxSize().padding(24.dp)) {
            Text(
                "Craftbook",
                style = MaterialTheme.typography.displaySmall,
                fontWeight = FontWeight.Bold,
                color = colors.inkWhite
            )
            Text(
                "Your finished work showcase",
                style = MaterialTheme.typography.bodyMedium,
                color = colors.inkAsh
            )

            Spacer(Modifier.height(24.dp))

            if (state.photos.isEmpty()) {
                // ── Empty State ──
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            Icons.Rounded.PhotoLibrary,
                            contentDescription = null,
                            tint = colors.inkAsh,
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "No projects yet",
                            style = MaterialTheme.typography.headlineMedium,
                            color = colors.inkSilver
                        )
                        Text(
                            "Tap + to add photos of your finished work",
                            style = MaterialTheme.typography.bodyMedium,
                            color = colors.inkAsh,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                // ── Polaroid Grid ──
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.photos, key = { it.rid }) { craft ->
                        CraftPolaroid(
                            craft = craft,
                            onRemove = { onRemove(craft) }
                        )
                    }
                }
            }
        }

        // ── Add FAB ──
        FloatingActionButton(
            onClick = { imagePicker.launch("image/*") },
            containerColor = colors.honey,
            contentColor = colors.canvas,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
                .size(64.dp),
            shape = CircleShape
        ) {
            Icon(Icons.Rounded.Add, "Add Photo", modifier = Modifier.size(28.dp))
        }
    }
}

@Composable
private fun CraftPolaroid(
    craft: CraftRecord,
    onRemove: () -> Unit,
) {
    val colors = Artisan.palette
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy),
        label = "polaroid_press"
    )

    PolaroidFrame(
        modifier = Modifier.scale(scale),
        borderColor = colors.driftwood
    ) {
        Box {
            SubcomposeAsyncImage(
                model = craft.photoUri,
                contentDescription = craft.caption,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(2.dp)),
                loading = {
                    ShimmerBox(Modifier.fillMaxSize(), cornerRadius = 2.dp)
                }
            )

            // Remove button
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(4.dp)
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.5f))
                    .clickable { onRemove() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Rounded.Close,
                    "Remove",
                    tint = Color.White,
                    modifier = Modifier.size(14.dp)
                )
            }
        }

        // Caption below the image (inside the polaroid bottom border)
        Text(
            text = craft.caption,
            style = MaterialTheme.typography.labelSmall,
            color = colors.inkSilver,
            maxLines = 1,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 2.dp)
                .offset(y = 20.dp)
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun CraftbookPreview() {
    val mockPhotos = listOf(
        CraftRecord(rid = 1, photoUri = "content://mock/photo1", caption = "Kitchen Cabinet"),
        CraftRecord(rid = 2, photoUri = "content://mock/photo2", caption = "Bed Frame"),
        CraftRecord(rid = 3, photoUri = "content://mock/photo3", caption = "Bookshelf"),
    )
    ArtisanTheme {
        CraftbookScreen(
            state = CraftbookState(photos = mockPhotos),
            onToggleDialog = {}, onCaptionChange = {},
            onPhotoSelected = {}, onRemove = {},
        )
    }
}
