package com.example.myapplication

import android.app.Application
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.HabitRepository
import com.example.myapplication.database.HabitViewModel
import com.example.myapplication.database.HabitViewModelFactory
import com.example.myapplication.ui.CreateHabitScreen
import com.example.myapplication.ui.MainScreen
import com.example.myapplication.ui.SettingsScreen


class HabitMakerApplication : Application() {
    private val database by lazy { AppDataBase.getDataBase(this) }
    val habitRepository by lazy { HabitRepository(database.habitDao()) }

}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AppCompatActivity() {

    private val habitViewModel: HabitViewModel by viewModels {
        HabitViewModelFactory((application as HabitMakerApplication).habitRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val startDestination = "MainScreen"
            MaterialTheme {

                Surface(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = startDestination,
                    ) {
                        composable (
                            route = "MainScreen",
                        ) {
                            MainScreen(
                                navController = navController,
                                habitViewModel = habitViewModel,
                            )
                        }

                        composable (
                            route = "CreateHabitScreen",
                        ) {
                            CreateHabitScreen(
                                navController = navController,
                                habitViewModel = habitViewModel,
                                )
                        }

                        composable (
                            route = "SettingsScreen",
                        ) {
                            SettingsScreen(
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}

