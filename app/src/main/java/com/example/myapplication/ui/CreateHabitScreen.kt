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
import androidx.compose.material3.TooltipAnchorPosition
import androidx.compose.material3.TooltipDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource

import androidx.navigation.NavController
import com.example.myapplication.R

import com.example.myapplication.database.Habit
import com.example.myapplication.database.HabitInsert
import com.example.myapplication.database.HabitViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun CreateHabitScreen(
    navController: NavController,
    habitViewModel: HabitViewModel,
) {
    val scrollState = rememberScrollState()
    val tooltipPosition = TooltipDefaults.rememberTooltipPositionProvider(
        TooltipAnchorPosition.Above
    )
    val ctx = LocalContext.current

    var habit: Habit? = null

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
                        habit?.let { habit ->
                            if (habitFormValid(habit)) {
                                val insert =
                                    HabitInsert(
                                        name = habit.name,
                                        frequency = habit.frequency,
                                        timesPerFrequency = habit.timesPerFrequency,
                                        notes = habit.notes,
                                        context = habit.context,
                                        encouragement = habit.encouragement,
                                        targetTimes = habit.targetTimes,
                                    )
                                val insertedHabitId = habitViewModel.insertHabit(insert)

                                // The id is -1 if its a failed insert
                                if (insertedHabitId != -1L) {
                                    // Insert the reminders

                                    // Reschedule the reminders for that habit


                                    // Insert the encouragements


                                    navController.navigate("MainScreen") {
                                        popUpTo("CreateHabitScreen")
                                    }
                                } else {
                                    Toast
                                        .makeText(
                                            ctx,
                                            ctx.getString(R.string.habit_already_exists),
                                            Toast.LENGTH_SHORT,
                                        ).show()
                                }
                            }
                        }
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
    val tooltipPosition = TooltipDefaults.rememberTooltipPositionProvider(
        TooltipAnchorPosition.Above
    )
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
