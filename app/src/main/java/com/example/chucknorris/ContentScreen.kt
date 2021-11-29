package com.example.chucknorris

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.chucknorris.ui.theme.Turquoise
import com.example.chucknorris.ui.theme.White

@ExperimentalComposeUiApi
@Composable
fun JokesScreen() {
    val configuration = LocalConfiguration.current

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            JokesScreenLand()
        }
        else -> {
            JokesScreenPort()
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun JokesScreenPort(jokesViewModel: JokesViewModel = viewModel()) {
    val joke by jokesViewModel.resultLive.observeAsState()

    LazyColumn(
        content = {
            if (joke != null) {
                for (i in joke!!) {
                    item {
                        Text(
                            text = i,
                            color = Color.Black,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .padding(
                                    top = 6.dp,
                                    bottom = 4.dp,
                                    start = 12.dp,
                                    end = 12.dp
                                ),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        },
        modifier = Modifier.padding(bottom = 260.dp)
    )

    val text = remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 520.dp)
    ) {
        TextField(
            modifier = Modifier
                .padding(start = 88.dp)
                .width(220.dp),
            value = text.value,
            onValueChange = { if (it.text.length <= 2) text.value = it },
            placeholder = { Text("Count") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Turquoise,
                backgroundColor = White,
                placeholderColor = Turquoise
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    jokesViewModel.getCurrentData(text.value.text); keyboardController?.hide(); focusManager.clearFocus()
                }),
            singleLine = true,

            )

        Button(
            onClick = { jokesViewModel.getCurrentData(text.value.text) },
            modifier = Modifier
                .padding(start = 120.dp, bottom = 100.dp, top = 16.dp)
                .width(150.dp)
                .height(60.dp)
        ) { Text(text = "RELOAD") }
    }
}

@ExperimentalComposeUiApi
@Composable
fun JokesScreenLand(jokesViewModel: JokesViewModel = viewModel()) {
    val joke by jokesViewModel.resultLive.observeAsState()
    LazyColumn(
        content = {
            if (joke != null) {
                for (i in joke!!) {
                    item {
                        Text(
                            text = i,
                            color = Color.Black,
                            textAlign = TextAlign.Justify,
                            modifier = Modifier
                                .padding(
                                    top = 6.dp,
                                    bottom = 4.dp,
                                    start = 12.dp,
                                    end = 12.dp
                                ),
                            fontSize = 20.sp
                        )
                    }
                }
            }
        },
        modifier = Modifier.padding(end = 280.dp, bottom = 55.dp)
    )

    val text = remember { mutableStateOf(TextFieldValue("")) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current


    Column(
        modifier = Modifier
            .padding(start = 590.dp)
    ) {
        TextField(
            modifier = Modifier
                .padding(start = 10.dp, top = 25.dp, bottom = 50.dp)
                .width(100.dp),
            value = text.value,
            onValueChange = { if (it.text.length <= 2) text.value = it },
            placeholder = { Text("Count") },
            colors = TextFieldDefaults.textFieldColors(
                textColor = Turquoise,
                backgroundColor = White,
                placeholderColor = Turquoise
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    jokesViewModel.getCurrentData(text.value.text); keyboardController?.hide(); focusManager.clearFocus()
                }),
            singleLine = true,

            )

        Button(
            onClick = { jokesViewModel.getCurrentData(text.value.text) },
            modifier = Modifier
                .padding(start = 10.dp, bottom = 80.dp, top = 16.dp)
                .width(120.dp)
                .height(60.dp)
        ) { Text(text = "RELOAD") }
    }
}


@Composable
fun WebScreen() {
    AndroidView(factory = {
        WebView(it).apply {
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView?,
                    request: WebResourceRequest?
                ): Boolean {
                    return false
                }
            }
        }
    }, update = {
        it.loadUrl("https://www.icndb.com/api/")
    })
}