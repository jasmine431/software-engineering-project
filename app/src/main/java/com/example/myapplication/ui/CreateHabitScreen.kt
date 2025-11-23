package com.example.myapplication.ui

import android.widget.Toast
import androidx.compose.foundation.BasicTooltipBox
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberBasicTooltipState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Save
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.asLiveData
import androidx.navigation.NavController
import com.example.myapplication.R
//import com.dessalines.habitmaker.db.AppSettingsViewModel
import com.example.myapplication.database.Encouragement
import com.example.myapplication.database.EncouragementInsert
import com.example.myapplication.database.EncouragementViewModel
import com.example.myapplication.database.Habit
import com.example.myapplication.database.HabitInsert
import com.example.myapplication.database.HabitReminder
import com.example.myapplication.database.HabitReminderInsert
import com.example.myapplication.database.HabitReminderViewModel
import com.example.myapplication.database.HabitViewModel
//import com.dessalines.habitmaker.notifications.scheduleRemindersForHabit
//import com.dessalines.habitmaker.ui.components.common.BackButton
//import com.dessalines.habitmaker.ui.components.common.ToolTip
import java.time.DayOfWeek

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CreateHabitScreen(
    navController: NavController,
    //appSettingsViewModel: AppSettingsViewModel,
    habitViewModel: HabitViewModel?,
    encouragementViewModel: EncouragementViewModel?,
    reminderViewModel: HabitReminderViewModel?,
) {
//    val settings by appSettingsViewModel.appSettings.asLiveData().observeAsState()
//    val firstDayOfWeek = settings?.firstDayOfWeek ?: DayOfWeek.SUNDAY
//    val firstDayOfWeek = DayOfWeek.SUNDAY
    val scrollState = rememberScrollState()
    val tooltipPosition = TooltipDefaults.rememberPlainTooltipPositionProvider()
    val ctx = LocalContext.current

    var habit: Habit? = null
//    var encouragements: List<Encouragement> = listOf()
//    var reminders: List<HabitReminder> = listOf()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.create_habit)) },
                navigationIcon = {
                    BackButton(
                        onBackClick = { navController.navigateUp() },
                    )
                },
            )
        },
        content = { padding ->
            Column(
                modifier =
                    Modifier
                        .padding(padding)
                        .verticalScroll(scrollState)
                        .imePadding(),
            ) {
                HabitForm(
                    onChange = { habit = it },
                )

            }
        },
        floatingActionButton = {
            BasicTooltipBox(
                positionProvider = tooltipPosition,
                state = rememberBasicTooltipState(isPersistent = false),
                tooltip = {
                    ToolTip(stringResource(R.string.save))
                },
            ) {
                FloatingActionButton(
                    modifier = Modifier.imePadding(),
                    onClick = {
//
                    },
                    shape = CircleShape,
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Save,
                        contentDescription = stringResource(R.string.save),
                    )
                }
            }
        },
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun BackButton(onBackClick: () -> Unit) {
    val tooltipPosition = TooltipDefaults.rememberPlainTooltipPositionProvider()
    BasicTooltipBox(
        positionProvider = tooltipPosition,
        state = rememberBasicTooltipState(isPersistent = false),
        tooltip = {
            ToolTip(stringResource(R.string.go_back))
        },
    ) {
        IconButton(
            onClick = onBackClick,
        ) {
            Icon(
                Icons.AutoMirrored.Outlined.ArrowBack,
                contentDescription = stringResource(R.string.go_back),
            )
        }
    }
}

@Composable
fun ToolTip(text: String) {
    ElevatedCard {
        Text(
            text = text,
            modifier = Modifier.padding(SMALL_PADDING),
        )
    }
}
