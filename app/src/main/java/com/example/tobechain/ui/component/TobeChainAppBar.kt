package com.example.tobechain.ui.component

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.ModeEdit
import androidx.compose.material.icons.outlined.PowerSettingsNew
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tobechain.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun showMapAppBar(context: Context, snackbarHostState: SnackbarHostState, scope: CoroutineScope) {

    var editModeStatus = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
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
                color = MaterialTheme.colorScheme.outline
            )
            Icon(
                modifier = Modifier.padding(end = 16.dp),
                imageVector = Icons.Outlined.PowerSettingsNew,
                contentDescription = stringResource(id = R.string.ic_poweroff),
                tint = MaterialTheme.colorScheme.outline
            )

            Icon(
                modifier = Modifier.padding(end = 8.dp),
                imageVector = Icons.Outlined.Logout,
                contentDescription = stringResource(id = R.string.ic_logout),
                tint = MaterialTheme.colorScheme.outline
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // start
            LazyRow (
                verticalAlignment = Alignment.CenterVertically,
            ) {
                item {
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
                        color = MaterialTheme.colorScheme.outline
                    )
                    Switch(
                        modifier = Modifier.padding(10.dp),
                        checked = editModeStatus.value,
                        onCheckedChange = { check ->
                            editModeStatus.value = check
                            scope.launch {
                                showEditSnackBar(snackbarHostState, context, check)
                            }

                        }
                    )
                }
            }

            // end
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = stringResource(id = R.string.ic_locationon),
                    modifier = Modifier.padding(end = 8.dp),
                    tint = MaterialTheme.colorScheme.error
                )
                Text(
                    text = stringResource(id = R.string.txt_map),
                    modifier = Modifier.padding(end = 8.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.outline,
                    ),
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
    if(value) {
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
