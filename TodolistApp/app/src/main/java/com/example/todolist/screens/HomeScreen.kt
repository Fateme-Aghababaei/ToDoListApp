package com.example.todolist.screens

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.models.Task
import com.example.todolist.viewModel.TaskViewModel
import com.example.todolist.viewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel,
    userViewModel: UserViewModel,
    token: String,
    onAddTaskClicked: () -> Unit,
    refreshOnClick: () -> Unit,
    onLogout: () -> Unit
) {

    val allTasks by taskViewModel.allTasks.collectAsState()
    val logoutSuccess by userViewModel.logoutSuccess.collectAsState()
    val user by userViewModel.loggedInUser.collectAsState()

    LaunchedEffect(key1 = allTasks) {
        taskViewModel.getAllTasks(token)
    }

    LaunchedEffect(key1 = logoutSuccess) {
        if (logoutSuccess) {
            onLogout()
            userViewModel.resetLogoutSuccess()
        }
    }

    LaunchedEffect(key1 = user) {
        Log.v("fatt", "home user: $user")
        userViewModel.getUser(token)
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(topBar = {
            TopAppBar(
                title = {
                    Text(text = "کارات", style = MaterialTheme.typography.titleLarge)
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.tertiary,
                ),
                actions = {
                    var menuExpanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Outlined.AccountCircle,
                            contentDescription = "profile",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    DropdownMenu(expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.AccountCircle,
                                    contentDescription = "Avatar",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(48.dp)
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = user?.email ?: "",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedButton(
                                onClick = {
                                    Log.v("fatt", "Logout button clicked")
                                    userViewModel.logout(token)
                                },
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 8.dp)
                            ) {
                                Text(text = "خروج از حساب کاربری")
                            }
                        }
                    }
                },
            )
        }, floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onAddTaskClicked()
                }, containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }) {
            LazyColumn(
                modifier = modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                items(items = allTasks) { task ->
                    TaskUI(modifier = Modifier, task = task, taskViewModel, token, refreshOnClick)
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TaskUI(
    modifier: Modifier,
    task: Task,
    taskViewModel: TaskViewModel,
    token: String,
    refreshOnClick: () -> Unit
) {
    var isChecked by remember {
        mutableStateOf(task.is_completed)
    }
    val context = LocalContext.current

    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp, 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (task.priority) {
                1 -> Color(0xFFFFEAEA)
                2 -> Color(0xFFFFFBEA)
                3 -> Color(0xFFEAFFED)
                else -> Color(0xFFF5F5F5)
            }
        ),
//        content = {
//
//        }
    ) {
        Row(modifier = modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = 10 * density
                val color = when (task.priority) {
                    1 -> Color(0xFFC43E3E)
                    2 -> Color(0xFFF5C149)
                    3 -> Color(0xFF57AD40)
                    else -> Color(0xFF939593)
                }
                drawLine(
                    color,
                    Offset(size.width, strokeWidth / 2),
                    Offset(size.width, size.height - strokeWidth / 2),
                    strokeWidth
                )
            }
            .padding(16.dp, 8.dp, 8.dp, 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Column(
                modifier = modifier.widthIn(150.dp, 200.dp)
            ) {
                Text(text = task.title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(text = "تاریخ: ${task.due_date}", style = MaterialTheme.typography.bodyMedium)
                LazyRow {
                    for (tag in task.tags) {
                        item {
                            AssistChip(
                                onClick = { /*TODO*/ },
                                label = {
                                    Text(
                                        text = tag.title,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                            )
                            Spacer(Modifier.width(4.dp))
                        }
                    }
                }
            }


            Row {
                IconButton(onClick = {
                    taskViewModel.deleteTask(token, task.id)
                    refreshOnClick()
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

                Checkbox(checked = isChecked, onCheckedChange = {
                    isChecked = it
                    task.is_completed = it
                    taskViewModel.changeTaskStatus(token, task.id, task.is_completed)
                })
            }
        }
    }
}

@Composable
fun DrawerContent(
    onProfileClicked: () -> Unit, onLogoutClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = "Avatar",
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
            Text(text = "پست الکترونیک", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = onLogoutClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Text(text = "خروج از حساب کاربری")
        }
    }
}
