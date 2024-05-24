package com.example.todolist.screens

import android.content.Intent
import androidx.compose.ui.platform.LocalContext
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material.icons.outlined.Share
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.R
import com.example.todolist.models.Task
import com.example.todolist.viewModel.TaskViewModel
import com.example.todolist.viewModel.UserViewModel
/**
 * Composable function for displaying the home screen.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param taskViewModel ViewModel for managing tasks.
 * @param userViewModel ViewModel for managing user-related operations.
 * @param token Authentication token.
 * @param onAddTaskClicked Callback for when the "Add Task" button is clicked.
 * @param refreshOnClick Callback for refreshing the UI.
 * @param onLogout Callback for logging out the user.
 */
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

    // Collecting state from the taskViewModel and userViewModel
    val allTasks by taskViewModel.allTasks.collectAsState()
    val logoutSuccess by userViewModel.logoutSuccess.collectAsState()
    val user by userViewModel.loggedInUser.collectAsState()

    // Fetching all tasks when the screen is launched
    LaunchedEffect(key1 = allTasks) {
        taskViewModel.getAllTasks(token)
    }

    // Logging out user when logout success state changes
    LaunchedEffect(key1 = logoutSuccess) {
        if (logoutSuccess) {
            onLogout()
            userViewModel.resetLogoutSuccess()
        }
    }

    // Fetching user information when user state changes
    LaunchedEffect(key1 = user) {
        Log.v("fatt", "home user: $user")
        userViewModel.getUser(token)
    }

    // Providing layout direction for the screen
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(topBar = {
            // Creating a top app bar with profile dropdown menu
            TopAppBar(
                title = {
                    Text(text = "کارات", style = MaterialTheme.typography.titleLarge)
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = Color.Black
                ),
                actions = {
                    // Profile dropdown menu
                    var menuExpanded by remember { mutableStateOf(false) }
                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Outlined.PersonOutline,
                            contentDescription = "profile",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                    DropdownMenu(expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            // Displaying user profile information
                            Box(
                                modifier = Modifier.fillMaxWidth(),
                                contentAlignment = Alignment.Center
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.profile),
                                    contentDescription = "profile"
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
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
                            Spacer(modifier = Modifier.height(4.dp))
                            // Logout button
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
                                Text(
                                    text = "خروج از حساب کاربری",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                },
            )
        }, floatingActionButton = {
            // Floating action button for adding tasks
            FloatingActionButton(
                onClick = {
                    onAddTaskClicked()
                }, containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add", modifier = modifier.size(32.dp))
            }
        }) {
            // Displaying list of tasks using LazyColumn
            LazyColumn(
                modifier = modifier.padding(it),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            ) {
                items(items = allTasks) { task ->
                    // Displaying each task item
                    TaskUI(modifier = Modifier, task = task, taskViewModel, token, refreshOnClick)
                }
                item {
                    // Adding space at the end of the list
                    Spacer(modifier = Modifier.height(64.dp))
                }
            }
        }
    }
}

/**
 * Composable function for displaying a single task item.
 *
 * @param modifier Modifier to be applied to the layout.
 * @param task Task object to be displayed.
 * @param taskViewModel ViewModel for managing tasks.
 * @param token Authentication token.
 * @param refreshOnClick Callback for refreshing the UI.
 */
@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun TaskUI(
    modifier: Modifier,
    task: Task,
    taskViewModel: TaskViewModel,
    token: String,
    refreshOnClick: () -> Unit
) {
    // State for checkbox state (checked or unchecked)
    var isChecked by remember {
        mutableStateOf(task.is_completed)
    }
    val context = LocalContext.current

    // Card to display task details
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
        )
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
                // Displaying task title
                Text(
                    text = task.title,
                    style = MaterialTheme.typography.titleMedium.copy(textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None),
                    color = if (isChecked) Color.Gray else MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Displaying task description
                Text(
                    text = task.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 3,
                    overflow = Text
Overflow.Ellipsis
                )
                // Displaying task due date
                Text(text = "تاریخ: ${task.due_date}", style = MaterialTheme.typography.bodyMedium)
                // Displaying task tags
                LazyRow {
                    for (tag in task.tags) {
                        item {
                            AssistChip(
                                onClick = { },
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

            // Action buttons for task
            Row {
                // Button to delete task
                IconButton(onClick = {
                    taskViewModel.deleteTask(token, task.id)
                    refreshOnClick()
                }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Delete",
                        tint = Color.DarkGray
                    )
                }

                // Button to share task details
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
                        imageVector = Icons.Outlined.Share,
                        contentDescription = "Share",
                        tint = Color.DarkGray
                    )
                }

                // Checkbox for marking task as completed
                Checkbox(checked = isChecked, onCheckedChange = {
                    isChecked = it
                    task.is_completed = it
                    taskViewModel.changeTaskStatus(token, task.id, task.is_completed)
                    //taskViewModel.getAllTasks(token)
                })
            }
        }
    }
}
