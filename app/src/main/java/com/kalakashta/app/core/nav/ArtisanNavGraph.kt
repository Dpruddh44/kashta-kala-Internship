package com.kalakashta.app.core.nav

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.Build
import androidx.compose.material.icons.rounded.MenuBook
import androidx.compose.material.icons.rounded.Receipt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kalakashta.app.core.designsystem.Artisan
import com.kalakashta.app.feature.craftbook.CraftbookEngine
import com.kalakashta.app.feature.craftbook.CraftbookScreen
import com.kalakashta.app.feature.inspiration.InspirationEngine
import com.kalakashta.app.feature.inspiration.InspirationScreen
import com.kalakashta.app.feature.ledger.LedgerEngine
import com.kalakashta.app.feature.ledger.LedgerScreen
import com.kalakashta.app.feature.workbench.WorkbenchEngine
import com.kalakashta.app.feature.workbench.WorkbenchScreen

// ── Route Definitions ──
enum class Screen(val route: String, val label: String, val icon: ImageVector) {
    INSPIRATION("inspiration", "Inspire", Icons.Rounded.AutoAwesome),
    WORKBENCH("workbench", "Workbench", Icons.Rounded.Build),
    LEDGER("ledger", "Ledger", Icons.Rounded.Receipt),
    CRAFTBOOK("craftbook", "Craftbook", Icons.Rounded.MenuBook),
}

@Composable
fun ArtisanNavGraph(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val colors = Artisan.palette

    Scaffold(
        containerColor = colors.canvas,
        bottomBar = { ArtisanBottomBar(navController) },
        modifier = modifier,
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.INSPIRATION.route,
            modifier = Modifier.padding(innerPadding),
            enterTransition = { fadeIn() + slideInVertically { it / 8 } },
            exitTransition = { fadeOut() },
        ) {
            composable(Screen.INSPIRATION.route) {
                val vm: InspirationEngine = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                InspirationScreen(
                    state = state,
                    onFamilyPick = vm::pickFamily,
                    onSearchChange = vm::updateSearch,
                    onSearchSubmit = vm::submitSearch,
                    onHeartTap = vm::toggleHeart,
                )
            }

            composable(Screen.WORKBENCH.route) {
                val vm: WorkbenchEngine = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                WorkbenchScreen(
                    state = state,
                    onLengthChange = vm::setLength,
                    onWidthChange = vm::setWidth,
                    onThicknessChange = vm::setThickness,
                    onTimberPick = vm::pickTimber,
                    onToggleTimberSheet = vm::toggleTimberSheet,
                    onCompute = vm::compute,
                    canCompute = vm.canCompute,
                )
            }

            composable(Screen.LEDGER.route) {
                val vm: LedgerEngine = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                LedgerScreen(
                    state = state,
                    onItemLabel = vm::setItemLabel,
                    onClientName = vm::setClientName,
                    onLabourRate = vm::setLabourRate,
                    onMaterialCost = vm::setMaterialCostManual,
                    onMarginChange = vm::setMargin,
                    onRecalc = vm::recalculate,
                    onSave = vm::saveInvoice,
                )
            }

            composable(Screen.CRAFTBOOK.route) {
                val vm: CraftbookEngine = viewModel()
                val state by vm.state.collectAsStateWithLifecycle()
                CraftbookScreen(
                    state = state,
                    onToggleDialog = vm::toggleAddDialog,
                    onCaptionChange = vm::setCaption,
                    onPhotoSelected = vm::addPhoto,
                    onRemove = vm::removePhoto,
                )
            }
        }
    }
}

@Composable
private fun ArtisanBottomBar(navController: NavHostController) {
    val colors = Artisan.palette
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = backStackEntry?.destination?.route

    NavigationBar(
        containerColor = colors.surface,
        contentColor = colors.inkWhite,
        tonalElevation = 0.dp,
    ) {
        Screen.entries.forEach { screen ->
            val selected = currentRoute == screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (currentRoute != screen.route) {
                        navController.navigate(screen.route) {
                            popUpTo(Screen.INSPIRATION.route) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                icon = {
                    Icon(
                        screen.icon,
                        contentDescription = screen.label,
                    )
                },
                label = {
                    Text(
                        screen.label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colors.spark,
                    selectedTextColor = colors.spark,
                    unselectedIconColor = colors.inkAsh,
                    unselectedTextColor = colors.inkAsh,
                    indicatorColor = colors.sparkGhost,
                )
            )
        }
    }
}
