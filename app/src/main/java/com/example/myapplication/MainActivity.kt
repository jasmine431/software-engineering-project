package com.example.myapplication

import android.app.Application
import android.content.BroadcastReceiver
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.viewModels
//import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.myapplication.database.AppDataBase
import com.example.myapplication.database.HabitRepository
import com.example.myapplication.database.HabitViewModel
import com.example.myapplication.database.HabitViewModelFactory
import com.example.myapplication.database.HabitCheckRepository
import com.example.myapplication.database.HabitCheckViewModel
import com.example.myapplication.database.HabitCheckViewModelFactory
import com.example.myapplication.database.HabitReminderRepository
import com.example.myapplication.database.HabitReminderViewModel
import com.example.myapplication.database.HabitReminderViewModelFactory
import com.example.myapplication.database.EncouragementRepository
import com.example.myapplication.database.EncouragementViewModel
import com.example.myapplication.database.EncouragementViewModelFactory
import com.example.myapplication.ui.CreateHabitScreen
import com.example.myapplication.ui.MainScreen

//import kotlin.getValue


class HabitMakerApplication : Application() {
    private val database by lazy { AppDataBase.getDataBase(this) }
//    val appSettingsRepository by lazy { AppSettingsRepository(database.appSettingsDao()) }
    val habitRepository by lazy { HabitRepository(database.habitDao()) }
    val encouragementRepository by lazy { EncouragementRepository(database.encouragementDao()) }
    val habitCheckRepository by lazy { HabitCheckRepository(database.habitCheckDao()) }
    val habitReminderRepository by lazy { HabitReminderRepository(database.habitReminderDao()) }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
class MainActivity : AppCompatActivity() {

    private val habitViewModel: HabitViewModel by viewModels {
        HabitViewModelFactory((application as HabitMakerApplication).habitRepository)
    }

    private val encouragementViewModel: EncouragementViewModel by viewModels {
        EncouragementViewModelFactory((application as HabitMakerApplication).encouragementRepository)
    }

    private val habitCheckViewModel: HabitCheckViewModel by viewModels {
        HabitCheckViewModelFactory((application as HabitMakerApplication).habitCheckRepository)
    }

    private val reminderViewModel: HabitReminderViewModel by viewModels {
        HabitReminderViewModelFactory((application as HabitMakerApplication).habitReminderRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
//        setContentView(R.layout.activity_main)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        setContent {
            MaterialTheme {
                val startDestination = "MainScreen"

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
                            )
                        }

                        composable (
                            route = "CreateHabitScreen",
                        ) {
                            CreateHabitScreen(
                                navController = navController,
                                habitViewModel = habitViewModel,
                                encouragementViewModel = encouragementViewModel,
                                reminderViewModel = reminderViewModel

                            )
                        }
                    }
                }
            }
        }




    }


}

