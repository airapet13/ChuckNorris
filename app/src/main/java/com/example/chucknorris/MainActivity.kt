package com.example.chucknorris

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.view.WindowCompat
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.chucknorris.ui.theme.ChuckNorrisTheme
import com.example.chucknorris.ui.theme.Turquoise
import com.example.chucknorris.ui.theme.White

class MainActivity : ComponentActivity() {
    @ExperimentalComposeUiApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, true)
        setContent {
            ChuckNorrisTheme {
                MainScreen()
            }
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    Scaffold(
        topBar = { TopBar() },
        bottomBar = { BottomNavigationBar(navController) }
    ) {
        Navigation(navController = navController)
    }
}

@ExperimentalComposeUiApi
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}

@Composable
fun TopBar() {
    TopAppBar(
        title = { Text(text = "Jokes", fontSize = 20.sp) },
        backgroundColor = Turquoise,
        contentColor = White
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(
        NavigationItem.Jokes,
        NavigationItem.Web
    )
    BottomNavigation(Modifier.background(color = White), contentColor = Turquoise) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        modifier = Modifier.size(25.dp),
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) },
                selectedContentColor = White,
                unselectedContentColor = White.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@ExperimentalComposeUiApi
@Composable
fun Navigation(navController: NavHostController) {
    NavHost(navController, startDestination = NavigationItem.Jokes.route) {
        composable(NavigationItem.Jokes.route) {
            JokesScreen()
        }
        composable(NavigationItem.Web.route) {
            WebScreen()
        }
    }
}