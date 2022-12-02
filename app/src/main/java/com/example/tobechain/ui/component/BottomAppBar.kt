package com.example.tobechain.ui.component

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TobeChainBottomAppBar(snackbarHostState: SnackbarHostState, scope: CoroutineScope) {

    val actionBarItemsIcon = listOf(
        Icons.Outlined.ArrowUpward,
        Icons.Outlined.East,
        Icons.Outlined.OpenWith,
        Icons.Outlined.DeleteOutline,
        Icons.Outlined.CopyAll,
    )

    val actionBarItemsText = listOf(
        "층추가",
        "라인추가",
        "호추가",
        "초기화",
        "그룹영역",
    )

    BottomAppBar(
        modifier = Modifier
            .navigationBarsPadding()
            .padding(24.dp),
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.25f),
        actions = {

            actionBarItemsIcon.forEachIndexed { index, actionIcon ->
                FilledIconButton(
                    onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = actionBarItemsText[index],
                                actionLabel = "닫기",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                ) {
                    Icon(
                        imageVector = actionIcon,
                        contentDescription = null,
                        modifier = Modifier.size(SwitchDefaults.IconSize),
                    )
                }
            }

        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            message = "저장",
                            actionLabel = "닫기",
                            duration = SnackbarDuration.Short
                        )
                    }
                },
                //containerColor = BottomAppBarDefaults.bottomAppBarFabColor,
                //elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
            ) {
                Icon(Icons.Outlined.Save, contentDescription = null)
            }
        }
    )
}
