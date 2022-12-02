package com.example.tobechain.ui.component

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.tobechain.R
import com.example.tobechain.map.MapViewModel
import com.example.tobechain.ui.theme.notossanskr

@Composable
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
fun TobeChainNavigationRail(
    viewModel: MapViewModel,
    modifier: Modifier,
    iconItems: List<ImageVector>,
    menuItems: List<String>,
    selectedItem: MutableState<ImageVector>,
) {
    Row {
        NavigationRail(
            modifier = modifier,
            header = {
                Image(
                    if (isSystemInDarkTheme()) painterResource(id = R.drawable.ic_ci_white) else painterResource(id = R.drawable.ic_ci_dark),
                    null,
                    // adjust image
                    Modifier
                        .width(120.dp)
                        .height(80.dp)
                        .padding(vertical = 14.dp, horizontal = 16.dp),
                    //tint = MaterialTheme.colorScheme.primary
                )
            },
        ) {
            iconItems.forEachIndexed { index, item ->
                NavigationRailItem(
                    modifier = Modifier.padding(8.dp),
                    icon = {
                        //BadgedBox(badge = { Badge { Text("8") } }) {
                        Icon(
                            item,
                            contentDescription = menuItems[index]
                        )
                        //}
                    },
                    selected = selectedItem.value == item,
                    onClick = {
                        selectedItem.value = item
                        Log.d("debug", menuItems[index])
                    },
                    label = {
                        Text(
                            modifier = Modifier.padding(top = 30.dp),
                            text = menuItems[index],
                            fontFamily = notossanskr

                        )
                    },
                    alwaysShowLabel = false
                )
            }
            Spacer(Modifier.weight(1f))
        }

        TobeChainExplodedView(viewModel)
    }
}