package com.example.tobechain.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.style.TextOverflow
import com.example.tobechain.BuildConfig
import com.example.tobechain.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun showToolbar(
    context: MainActivity,
    drawerState: DrawerState,
    scope: CoroutineScope
) {

    val logoutOpenDialogStatus = remember { mutableStateOf(false) }
    LogoutDialog(
        "로그아웃",
        "로그아웃 하시겠습니까?",
        "확인",
        "취소",
        logoutOpenDialogStatus
    )

    TopAppBar(title = {
        Text(
            text = "${BuildConfig.APP_NAME} version ${BuildConfig.VERSION_NAME}",
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }, navigationIcon = {
        IconButton(onClick = {
            scope.launch {
                drawerState.open()
            }
        }) {
            Icon(Icons.Default.Menu, contentDescription = null)
        }
    }, actions = {
        IconButton(onClick = {
            logoutOpenDialogStatus.value = true
        }) {
            Icon(Icons.Outlined.Logout, contentDescription = null)
        }
    })
}