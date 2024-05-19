package com.example.todolist.screens

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.todolist.models.Task
import com.example.todolist.viewModel.TaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel,
    token: String,
    onProfileClicked: () -> Unit,
    onAddTaskClicked: () -> Unit
) {

    val allTasks by taskViewModel.allTasks.collectAsState()

    LaunchedEffect(key1 = allTasks) {
        taskViewModel.getAllTasks(token)
    }

//    val changeStatusState = taskViewModel.changeTaskStatusFailure
//    LaunchedEffect(key1 = changeStatusState) {
//        if (changeStatusState.value) {
//
//        }
//    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "کارات", style = MaterialTheme.typography.titleLarge)
                    },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                    ),
                    actions = {
                        IconButton(onClick = {
                            onProfileClicked()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.AccountCircle,
                                contentDescription = "profile",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        onAddTaskClicked()
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            }
        ) {
            LazyColumn(
                modifier = modifier
                    .padding(it),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                item {
                    TaskUI(
                        modifier = Modifier,
                        task = Task(
                            1,
                            "تست",
                            "تست توضیح",
                            "2021-12-13",
                            false,
                            1,
                            listOf(),
                            1
                        ),
                        taskViewModel = taskViewModel,
                        token = token
                    )
                }

                item {
                    TaskUI(
                        modifier = Modifier,
                        task = Task(
                            1,
                            "تست",
                            "تست توضیح",
                            "2021-12-13",
                            false,
                            2,
                            listOf(),
                            1
                        ),
                        taskViewModel = taskViewModel,
                        token = token
                    )
                }

                item {
                    TaskUI(
                        modifier = Modifier,
                        task = Task(
                            1,
                            "تست",
                            "تست توضیح",
                            "2021-12-13",
                            false,
                            3,
                            listOf(),
                            1
                        ),
                        taskViewModel = taskViewModel,
                        token = token
                    )
                }

                item {
                    TaskUI(
                        modifier = Modifier,
                        task = Task(
                            1,
                            "تست",
                            "تست توضیح",
                            "2021-12-13",
                            false,
                            0,
                            listOf(),
                            1
                        ),
                        taskViewModel = taskViewModel,
                        token = token
                    )
                }



                items(items = allTasks) { task ->
                    TaskUI(modifier = Modifier, task = task, taskViewModel, token)
                }
            }
        }
    }
}

//@SuppressLint("StateFlowValueCalledInComposition")
//@Composable
//fun TaskUI(modifier: Modifier, task: Task, taskViewModel: TaskViewModel, token: String) {
//    var isChecked by remember {
//        mutableStateOf(task.is_completed)
//    }
////    Row(
////        modifier = modifier
////            .padding(16.dp, 8.dp)
////            .fillMaxWidth()
////            .clip(RoundedCornerShape(8.dp))
////            .drawBehind {
////                val strokeWidth = 10 * density
////                drawLine(
////                    if(Priority == 1){
////                        Color.Red
////                    }else if(Priority == 2){
////                        Color.Yellow
////                    }
////                    ,
////                    Offset(size.width, strokeWidth),
////                    Offset(size.width, size.height),
////                    strokeWidth
////                )
////            }
////            .padding(16.dp, 8.dp),
////        horizontalArrangement = Arrangement.SpaceBetween,
////        verticalAlignment = Alignment.CenterVertically
////    )
//    Row(
//        modifier = Modifier
//            .padding(16.dp, 8.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(8.dp))
//            .drawBehind {
//                val strokeWidth = 10 * density
//                val color = when (task.priority) {
//                    1 -> Color.Red
//                    2 -> Color.Yellow
//                    3 -> Color.Green
//                    else -> Color.LightGray
//                }
//                drawLine(
//                    color,
//                    Offset(size.width, strokeWidth / 2),
//                    Offset(size.width, size.height - strokeWidth / 2),
//                    strokeWidth
//                )
//            }
//            .padding(16.dp, 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    )
//    {
//        Column {
//            Text(text = task.title)
//            Text(text = task.description)
//            Text(text = task.due_date)
//            for (tag in task.tags)
//                Text(text = tag.title)
//        }
//
//        Checkbox(
//            checked = isChecked,
//            onCheckedChange = {
//                isChecked = it
//                task.is_completed = it
//                taskViewModel.changeTaskStatus(token, task.id, task.is_completed)
//            }
//        )
//    }
//}

//@SuppressLint("StateFlowValueCalledInComposition")
//@Composable
//fun TaskUI(modifier: Modifier, task: Task, taskViewModel: TaskViewModel, token: String) {
//    var isChecked by remember {
//        mutableStateOf(task.is_completed)
//    }
//
//    Row(
//        modifier = Modifier
//            .padding(16.dp, 8.dp)
//            .fillMaxWidth()
//            .clip(RoundedCornerShape(8.dp))
//            .drawBehind {
//                val strokeWidth = 10 * density
//                val color = when (task.priority) {
//                    1 -> Color.Red
//                    2 -> Color.Yellow
//                    3 -> Color.Green
//                    else -> Color.LightGray
//                }
//                drawLine(
//                    color,
//                    Offset(size.width, strokeWidth / 2),
//                    Offset(size.width, size.height - strokeWidth / 2),
//                    strokeWidth
//                )
//            }
//            .padding(16.dp, 8.dp),
//        horizontalArrangement = Arrangement.SpaceBetween,
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Column {
//            Text(text = task.title)
//            Text(text = task.description)
//            Text(text = task.due_date)
//            for (tag in task.tags)
//                Text(text = tag.title)
//        }
//
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Column(horizontalAlignment = Alignment.CenterHorizontally) {
//                IconButton(onClick = { /* TODO: Add delete functionality */ }) {
//                    Icon(
//                        imageVector = Icons.Default.Delete,
//                        contentDescription = "Delete",
//                        tint = Color.DarkGray
//                    )
//                }
//
//                IconButton(onClick = { /* TODO: Add share functionality */ }) {
//                    Icon(
//                        imageVector = Icons.Default.Share,
//                        contentDescription = "Share",
//                        tint = Color.Gray
//                    )
//                }
//            }
//            Checkbox(
//
//                checked = isChecked,
//                onCheckedChange = {
//                    isChecked = it
//                    task.is_completed = it
//                    taskViewModel.changeTaskStatus(token, task.id, task.is_completed)
//                }
//            )
//        }
//    }
//}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TaskUI(modifier: Modifier, task: Task, taskViewModel: TaskViewModel, token: String) {
    var isChecked by remember {
        mutableStateOf(task.is_completed)
    }
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .padding(16.dp, 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .drawBehind {
                val strokeWidth = 10 * density
                val color = when (task.priority) {
                    1 -> Color.Red
                    2 -> Color.Yellow
                    3 -> Color.Green
                    else -> Color.LightGray
                }
                drawLine(
                    color,
                    Offset(size.width, strokeWidth / 2),
                    Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth
                )
            }
            .padding(16.dp, 8.dp),
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

        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {
                    // TODO: Add delete functionality
                }) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.DarkGray
                    )
                }

                IconButton(onClick = {
                    val shareText = "عنوان تسک: ${task.title}"
                    val intent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(Intent.EXTRA_TEXT, shareText)
                        type = "text/plain"
                    }
                    val shareIntent = Intent.createChooser(intent, null)
                    context.startActivity(shareIntent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = Color.Gray
                    )
                }
            }
            Checkbox(
                checked = isChecked,
                onCheckedChange = {
                    isChecked = it
                    task.is_completed = it
                    taskViewModel.changeTaskStatus(token, task.id, task.is_completed)
                }
            )
        }
    }
}
