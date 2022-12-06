package com.example.tobechain.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tobechain.R
import com.example.tobechain.map.MapViewModel
import com.example.tobechain.ui.theme.RoundShapes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun TobeMapAppBar(
    context: Context?,
    snackbarHostState: SnackbarHostState?,
    scope: CoroutineScope?,
    viewModel: MapViewModel
) {

    //val editModeStatus = remember { mutableStateOf(false) }
    val editModeStatus = viewModel.editModeStatus
    val editCheckIcon: (@Composable () -> Unit)? = if (editModeStatus.value) {
        {
            Icon(
                imageVector = Icons.Filled.Check,
                contentDescription = null,
                modifier = Modifier.size(SwitchDefaults.IconSize),
            )
        }
    } else {
        null
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
            .background(
                if (!isSystemInDarkTheme()) {
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.25f)
                } else {
                    MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
                }, RoundShapes.small
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Map,
                contentDescription = stringResource(id = R.string.ic_map),
                modifier = Modifier.padding(start = 10.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                text = stringResource(id = R.string.txt_searchaddress),
                modifier = Modifier
                    .weight(1f)
                    .padding(10.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Icon(
                modifier = Modifier.padding(start = 10.dp),
                imageVector = Icons.Outlined.ModeEdit,
                contentDescription = stringResource(id = R.string.ic_editnote),
                tint = MaterialTheme.colorScheme.primary
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = stringResource(id = R.string.txt_editstat),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Switch(
                modifier = Modifier.padding(10.dp),
                checked = editModeStatus.value,
                onCheckedChange = { check ->
                    viewModel.setEditModeStatus(check)
                    scope?.launch {
                        snackbarHostState?.let { state ->
                            context?.let { mainContext ->
                                showEditSnackBar(
                                    state,
                                    mainContext,
                                    check
                                )
                            }
                        }
                    }
                },
                thumbContent = editCheckIcon

            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(id = R.string.ic_locationon),
                    modifier = Modifier.padding(start = 10.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    //text = stringResource(id = R.string.txt_lonlat),
                    text = "위도: ${viewModel.lon.value}, 경도: ${viewModel.lat.value}",
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }

    }
}

private suspend fun showEditSnackBar(
    snackbarHostState: SnackbarHostState,
    context: Context,
    value: Boolean
) {

    // snackbar가 존재한다면 삭제
    if (snackbarHostState.currentSnackbarData != null) {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    if (value) {
        snackbarHostState.showSnackbar(
            message = context.getString(R.string.txt_editon),
            duration = SnackbarDuration.Short,
            actionLabel = context.getString(R.string.txt_close)
        )
    } else {
        snackbarHostState.showSnackbar(
            message = context.getString(R.string.txt_editoff),
            duration = SnackbarDuration.Short,
            actionLabel = context.getString(R.string.txt_close)
        )
    }
}

