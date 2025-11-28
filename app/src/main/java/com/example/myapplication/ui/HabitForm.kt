package com.example.myapplication.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.R
import com.example.myapplication.database.Habit
import com.example.myapplication.utils.HabitFrequency
import com.example.myapplication.utils.toDays
import me.zhanghai.compose.preference.ListPreference
import me.zhanghai.compose.preference.ListPreferenceType
import me.zhanghai.compose.preference.ProvidePreferenceTheme

@Composable
fun HabitForm(
    habit: Habit? = null,
    onChange: (Habit) -> Unit,
) {
    val ctx = LocalContext.current

    var name by rememberSaveable {
        mutableStateOf(habit?.name.orEmpty())
    }

    var frequency by rememberSaveable {
        mutableStateOf(HabitFrequency.entries[habit?.frequency ?: 0])
    }

    var timesPerFrequency by rememberSaveable {
        mutableStateOf(habit?.timesPerFrequency?.toString() ?: "")
    }

    var notes by rememberSaveable {
        mutableStateOf(habit?.notes.orEmpty())
    }

    var context by rememberSaveable {
        mutableStateOf(habit?.context.orEmpty())
    }

    var encouragement by rememberSaveable {
        mutableStateOf(habit?.encouragement.orEmpty())
    }

    var targetTimes by rememberSaveable {
        mutableStateOf(habit?.targetTimes?.toString() ?: "")
    }


    fun habitChange() =
        onChange(
            Habit(
                id = habit?.id ?: 0,
                name = name,
                frequency = frequency.ordinal,
                timesPerFrequency = timesPerFrequency.toIntOrNull() ?: 1,
                notes = notes,
                context = context,
                encouragement = encouragement,
                streak = habit?.streak ?: 0,
                lastCompletedTime = habit?.lastCompletedTime ?: 0,
                targetTimes = targetTimes.toIntOrNull() ?: 1,
                currentTimes = habit?.currentTimes ?: 0,
                completedToday = habit?.completedToday ?: false
            ),
        )

    Column(
        modifier = Modifier.padding(horizontal = SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING),
    ) {
        ProvidePreferenceTheme {
            val nameError = !requiredFieldIsValid(name)
            OutlinedTextField(
                label = { Text(stringResource(R.string.title)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                value = name,
                isError = nameError,
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.surfaceVariant,

                    ),
                trailingIcon = {
                    if (nameError) {
                        ErrorIcon()
                    }
                },
                onValueChange = {
                    name = it
                    habitChange()
                },
            )

            ListPreference(
                modifier = Modifier.textFieldBorder(),
                type = ListPreferenceType.DROPDOWN_MENU,
                value = frequency,
                onValueChange = {
                    frequency = it

                    // Force times per frequency to 1 if daily
                    if (frequency == HabitFrequency.Daily) {
                        timesPerFrequency = "1"
                    }
                    habitChange()
                },
                values = HabitFrequency.entries,
                valueToText = {
                    AnnotatedString(ctx.getString(it.resId))
                },
                title = {
                    Text(stringResource(frequency.resId))
                },

                )

            // Only show times count when frequency is not daily
            AnimatedVisibility(
                frequency != HabitFrequency.Daily,
            ) {
                val timesPerFreqError = !timesPerFrequencyIsValid(timesPerFrequency, frequency)
                OutlinedTextField(
                    label = { Text(stringResource(R.string.how_many_times)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    value = timesPerFrequency,
                    isError = timesPerFreqError,
                    colors = TextFieldDefaults.colors(
                        focusedLabelColor = MaterialTheme.colorScheme.surfaceVariant,
                    ),
                    trailingIcon = {
                        if (timesPerFreqError) {
                            ErrorIcon()
                        }
                    },
                    onValueChange = {
                        timesPerFrequency = it
                        habitChange()
                    },
                )
            }

            val targetTimesError = !requiredNumIsValid(targetTimes)
            OutlinedTextField(
                label = { Text(stringResource(R.string.target)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                value = targetTimes,
                isError = targetTimesError,
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                trailingIcon = {
                    if (targetTimesError) {
                        ErrorIcon()
                    }
                },
                onValueChange = {
                    targetTimes = it
                    habitChange()
                },
            )



            OutlinedTextField(
                label = { Text(stringResource(R.string.when_and_where_optional)) },
                modifier = Modifier.fillMaxWidth(),
                value = context,
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                onValueChange = {
                    context = it
                    habitChange()
                },
            )

            OutlinedTextField(
                label = { Text(stringResource(R.string.notes_optional)) },
                modifier = Modifier.fillMaxWidth(),
                value = notes,
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                onValueChange = {
                    notes = it
                    habitChange()
                },
            )

            OutlinedTextField(
                label = { Text(stringResource(R.string.encouragement_optional)) },
                modifier = Modifier.fillMaxWidth(),
                value = encouragement,
                colors = TextFieldDefaults.colors(
                    focusedLabelColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                onValueChange = {
                    encouragement = it
                    habitChange()
                },
            )
        }
    }
}

@Composable
@Preview
fun HabitFormPreview() {
    HabitForm(onChange = {})
}

fun requiredFieldIsValid(name: String): Boolean = name.isNotEmpty()

fun requiredNumIsValid(targetTimes: String): Boolean = (targetTimes.toIntOrNull() ?: -1) > 0

fun timesPerFrequencyIsValid(
    timesPerFrequency: String,
    frequency: HabitFrequency,
): Boolean = IntRange(1, frequency.toDays()).contains(timesPerFrequency.toIntOrNull())

fun habitFormValid(habit: Habit): Boolean =
    requiredFieldIsValid(habit.name) &&
            timesPerFrequencyIsValid(
                habit.timesPerFrequency.toString(),
                HabitFrequency.entries[habit.frequency],
            ) &&
            requiredNumIsValid(habit.targetTimes.toString())

@Composable
fun ErrorIcon() =
    Icon(
        imageVector = Icons.Default.Info,
        tint = MaterialTheme.colorScheme.error,
        contentDescription = null,
    )

@Composable
fun Modifier.textFieldBorder(): Modifier =
    this then
            Modifier
                .border(
                    width = OutlinedTextFieldDefaults.UnfocusedBorderThickness,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    shape = OutlinedTextFieldDefaults.shape,
                )

