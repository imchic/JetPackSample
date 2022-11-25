package com.example.tobechain.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tobechain.R

@Composable
fun showMapAppBar(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, CircleShape),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.Map,
            contentDescription = stringResource(id = R.string.search),
            modifier = Modifier.padding(start = 16.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = R.string.search_address),
            modifier = Modifier
                .weight(1f)
                .padding(16.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.outline
        )
        Icon(
            imageVector = Icons.Outlined.EditNote,
            contentDescription = stringResource(id = R.string.editIcon),
            modifier = Modifier.padding(end = 2.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = R.string.editstat),
            modifier = Modifier.padding(end = 8.dp),

            style = MaterialTheme.typography.bodyMedium.copy(
                color = MaterialTheme.colorScheme.outline,
                fontWeight = FontWeight.Bold
            ),
        )
        Switch(
            checked = true,
            onCheckedChange = { },
            modifier = Modifier.padding(end = 24.dp)
        )
        Icon(
            modifier = Modifier.padding(end = 16.dp),
            imageVector = Icons.Outlined.PowerSettingsNew,
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colorScheme.primary
        )

        Icon(
            modifier = Modifier.padding(end = 8.dp),
            imageVector = Icons.Outlined.Logout,
            contentDescription = stringResource(id = R.string.search),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showTobeAppBar(
    isFullScreen: Boolean,
    modifier: Modifier = Modifier,
    onBackPressed: () -> Unit
) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.inverseOnSurface
        ),
        title = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = if (isFullScreen) Alignment.CenterHorizontally
                else Alignment.Start
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    modifier = Modifier.padding(top = 4.dp),
                    text = "TobeChain",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.outline
                )
            }
        },
        navigationIcon = {
            if (isFullScreen) {
                FilledIconButton(
                    onClick = onBackPressed,
                    modifier = Modifier.padding(8.dp),
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button),
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        },
        actions = {
            IconButton(
                onClick = { /*TODO*/ },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(id = R.string.more_options_button),
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    )
}