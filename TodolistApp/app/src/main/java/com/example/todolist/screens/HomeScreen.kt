package com.example.todolist.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.example.todolist.models.Task
import com.example.todolist.viewModel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(modifier: Modifier = Modifier, taskViewModel: TaskViewModel) {
    val allTasks = remember {
        taskViewModel.allTasks
    }
    LaunchedEffect(key1 = allTasks) {
        allTasks.collect {
            if (it.isNotEmpty()) {
                Log.v("fatt", allTasks.value.toString())
            }
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text("Top app bar")
                    },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.onBackground,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { /* TODO */ },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) { it ->
            LazyColumn(
                modifier = Modifier
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                items(items = allTasks.value) { task ->
                    TaskUI(modifier = Modifier, task = task, onTaskCheckedChange = { updatedTask ->
                        taskViewModel.updateTaskState(updatedTask) // Update task in ViewModel
                    })
                }
            }
        }
    }
}

@Composable
fun TaskUI(modifier: Modifier, task: Task, onTaskCheckedChange: (Task) -> Unit) {
    Row(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(8.dp))
            .shadow(AppBarDefaults.TopAppBarElevation)
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.primary))
            .zIndex(1f)
            .padding(16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(text = task.title)
            Text(text = task.description)
            Text(text = task.due_date)
            for (tag in task.tags)
                Text(text = tag.title)
        }

        Checkbox(
            checked = task.is_completed,
            onCheckedChange = {
                onTaskCheckedChange(task.copy(is_completed = !task.is_completed))
                Log.v("fatt", "oad inja")

            }
        )

    }
}