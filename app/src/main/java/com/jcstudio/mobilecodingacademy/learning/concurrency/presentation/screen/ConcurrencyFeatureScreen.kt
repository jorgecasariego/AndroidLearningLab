package com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.navigation.ConcurrencyTab
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.viewmodel.ConcurrencyLabViewModel

@Composable
fun ConcurrencyFeatureScreen(
    viewModel: ConcurrencyLabViewModel = viewModel()
) {
    var selectedTab by remember {
        mutableStateOf(ConcurrencyTab.EXPERIMENT)
    }

    Scaffold(
        bottomBar = {
            ConcurrencyBottomBar(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                }
            )
        }
    ) { innerPading ->
        when(selectedTab) {
            ConcurrencyTab.EXPERIMENT -> {
                ConcurrencyLabScreen(
                    modifier = Modifier.padding(innerPading),
                    viewModel = viewModel
                )
            }
            ConcurrencyTab.TIMELINE -> {
                RaceConditionTimelineScreen(
                    modifier = Modifier.padding(innerPading),
                    viewModel = viewModel
                )
            }
        }
    }
}

@Composable
fun ConcurrencyBottomBar(
    selectedTab: ConcurrencyTab,
    onTabSelected: (ConcurrencyTab) -> Unit
) {
    NavigationBar {
        NavigationBarItem(
            selected = selectedTab == ConcurrencyTab.EXPERIMENT,
            onClick = {
                onTabSelected(ConcurrencyTab.EXPERIMENT)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.PlayArrow,
                    contentDescription = null
                )
            },
            label = {
                Text("Experiment")
            }
        )

        NavigationBarItem(
            selected = selectedTab == ConcurrencyTab.TIMELINE,
            onClick = {
                onTabSelected(ConcurrencyTab.TIMELINE)
            },
            icon = {
                Icon(
                    imageVector = Icons.Outlined.List,
                    contentDescription = null
                )
            },
            label = {
                Text("Timeline")
            }
        )
    }

}