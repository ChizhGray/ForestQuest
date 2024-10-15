package com.golapp.forestquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.golapp.forestquest.navigation.*
import com.golapp.forestquest.room.entities.Player
import com.golapp.forestquest.screens.start.StartScreen
import com.golapp.forestquest.screens.hub.*
import com.golapp.forestquest.ui.theme.ForestQuestTheme
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        getCharCodeInfo('¢')
        setContent {
            val navController = rememberNavController()
            ForestQuestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = Routes.StartScreen.route
                    ) {
                        composable(Routes.StartScreen.route) {
                            StartScreen(
                                vm = koinViewModel(),
                                onPlayClick = { player ->
                                    navController.navigate(
                                        Routes.HubScreen.setArgument<Player>(player)
                                    )
                                }
                            )
                        }
                        composable(Routes.HubScreen.route) {navBackStackEntry ->
                            navBackStackEntry.getArgument<Player>()?.let { player ->
                                HubScreen(
                                    vm = koinViewModel {
                                        parametersOf(player)
                                    },
                                    onBackClick = { navController.popBackStack() }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

fun getCharCodeInfo(char: Char): Pair<String, Char> {
    // Unicode table: https://unicode-table.com/en/
    val uni= String.format("u+%04x", char.code).uppercase()
    val result = uni to char
    println("The Unicode value of ${result.second} is: ${result.first}")
    return result
}