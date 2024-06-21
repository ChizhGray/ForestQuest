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

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
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
                                    vm = koinViewModel(),
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