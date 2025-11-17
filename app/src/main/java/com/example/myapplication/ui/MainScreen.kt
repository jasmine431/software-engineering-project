package com.example.myapplication.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.myapplication.database.Habit



@Composable
fun MainScreen() {
    val habits = listOf(
        Habit (
            id = 1,
            name = "drink water",
            frequency = 0,
            timesPerFrequency = 3,
            notes = null,
            archived = 0,
            points = 0,
            score = 0,
            streak = 1,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        ),
        Habit (
            id = 2,
            name = "reading",
            frequency = 0,
            timesPerFrequency = 3,
            notes = null,
            archived = 0,
            points = 0,
            score = 0,
            streak = 3,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        ),
        Habit (
            id = 3,
            name = "study",
            frequency = 0,
            timesPerFrequency = 3,
            notes = "加油",
            archived = 0,
            points = 0,
            score = 0,
            streak = 5,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        ),
        Habit (
            id = 4,
            name = "meditation",
            frequency = 0,
            timesPerFrequency = 3,
            notes = null,
            archived = 0,
            points = 0,
            score = 0,
            streak = 11,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        ),
        Habit (
            id = 5,
            name = "sleep",
            frequency = 0,
            timesPerFrequency = 3,
            notes = "不要熬夜",
            archived = 0,
            points = 0,
            score = 0,
            streak = 14,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        ),
        Habit (
            id = 6,
            name = "housework",
            frequency = 0,
            timesPerFrequency = 3,
            notes = null,
            archived = 0,
            points = 0,
            score = 0,
            streak = 19,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        ),
        Habit (
            id = 7,
            name = "work out",
            frequency = 0,
            timesPerFrequency = 3,
            notes = null,
            archived = 0,
            points = 0,
            score = 0,
            streak = 23,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        ),
        Habit (
            id = 8,
            name = "writing",
            frequency = 0,
            timesPerFrequency = 3,
            notes = null,
            archived = 0,
            points = 0,
            score = 0,
            streak = 19,
            completed = 0,
            context = null,
            lastStreakTime = 0,
            lastCompletedTime = 0
        )

    )

    Column {
        Header()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(habits) { habit ->
                HabitCard(habit = habit)
            }

    //        // 添加更多示例项目
    //        items(15) { index ->
    //            SampleCard(index = index + habits.size)
    //        }
        }
    }
}

@Composable
fun Header() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 35.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        IconButton(
            onClick = { },
            modifier = Modifier.width(40.dp)

        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "settings",
            )
        }

        Text(
            text = "habit",
            //color = MaterialTheme.colorScheme.surfaceVariant,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.displaySmall
        )

        IconButton(
            onClick = { },
            modifier = Modifier.width(40.dp)
        ) {

        }
    }


}

@Composable
fun HabitCard(habit: Habit) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ){

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = habit.name,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
//                    Text(
//                        text = "${habit.streak}",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = MaterialTheme.colorScheme.onSurfaceVariant,
//                        maxLines = if (expanded) 10 else 1
//
//                    )
                }
                IconButton(
                    onClick = { expanded = !expanded }
                ) {
                    Icon(
                        imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                        contentDescription = if (expanded) "收起" else "展开"
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "已连续坚持 ${habit.streak}天",
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (expanded) {
                Column {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        InfoItem (
                            icon = Icons.Default.Schedule,
                            title = "上次完成",
                            value = "上次完成:${habit.lastCompletedTime}"
                        )

                        InfoItem (
                            icon = Icons.Default.AutoAwesome,
                            title = "积分",
                            value = "积分:${habit.score}"
                        )

                        habit.notes?.let {
                            InfoItem(
                                icon = Icons.Default.CalendarMonth,
                                title = "天数",
                                value = it
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(

                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
//                        OutlinedButton(
//                            onClick = { }
//                        ) {
//                            Text("记录进度")
//                        }

                        Button(
                            onClick = { }
                        ) {
                            Text("标记完成")
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                }
            }

        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}
//
//@Composable
//fun SampleCard(index: Int) {
//    Card(
//        modifier = Modifier.fillMaxWidth(),
//        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
//    ) {
//        Column(
//            modifier = Modifier.padding(16.dp)
//        ) {
//            Text(
//                text = "示例项目 $index",
//                style = MaterialTheme.typography.bodyLarge
//            )
//            Text(
//                text = "这是第 $index 个示例项目的描述内容",
//                style = MaterialTheme.typography.bodySmall,
//                color = MaterialTheme.colorScheme.onSurfaceVariant
//            )
//        }
//    }
//}



