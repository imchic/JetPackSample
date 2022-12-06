package com.example.tobechain

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
//import androidx.navigation.compose.rememberNavController

@SuppressLint("ComposableNaming")
@ExperimentalMaterial3Api
@Composable
@Preview
fun showPage() {

    //val navController = rememberNavController()
    val (currentScreen, setCurrentScreen) = remember { mutableStateOf("") }

    val theme = MaterialTheme {
        TopAppBar(
            title = {
                Text(
                    text = "Setting",
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.white)
                )
            },
            navigationIcon = {
//                IconButton(onClick = { navController.popBackStack() }) {
//                    Icon(
//                        imageVector = Icons.Outlined.ArrowBack,
//                        contentDescription = "Back",
//                    )
//                }
            },
        )
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Setting",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = colorResource(id = R.color.black)
            )
        }
    }
}
