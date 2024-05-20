package com.example.todolist.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import com.example.todolist.viewModel.TaskViewModel
import java.util.Calendar

var items = listOf("برچسب ۱", "برچسب ۲", "برچسب ۳")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    modifier: Modifier = Modifier,
    taskViewModel: TaskViewModel,
    token: String,
    onCancelClicked: () -> Unit,
    onAddTaskClicked: (task: Task) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var dueTime by remember { mutableStateOf("") }
    var priority by remember { mutableIntStateOf(0) }
    var tags by remember { mutableStateOf(emptyList<Tag>()) }
    var selectedPriorityIndex by remember { mutableIntStateOf(0) }
    val context = LocalContext.current

    val allTags by taskViewModel.allTags.collectAsState()

    LaunchedEffect(key1 = allTags) {
        taskViewModel.getTags(token)
        Log.v("fatt", allTags.toString())
    }

    // Snack bar state
    var snackBarVisible by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "کارات",
                            style = MaterialTheme.typography.titleLarge.copy(color = Color.Black)
                        )
                    },
                    colors = topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        titleContentColor = Color.Green,
                    ),
                    actions = {
                        IconButton(onClick = {
                            onCancelClicked()
                        }) {
                            Icon(
                                imageVector = Icons.Outlined.Cancel,
                                contentDescription = "profile",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    },
                )
            }
        ) {
            LazyColumn(
                modifier = modifier
                    .padding(it)
                    .background(MaterialTheme.colorScheme.background),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // title
                item {
                    OutlinedTextField(
                        value = title,
                        onValueChange = {
                            title = it
                        },

                        label = { Text("عنوان", style = MaterialTheme.typography.bodyMedium) },
                        singleLine = true,
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth(),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    )
                }

                // description
                item {
                    OutlinedTextField(
                        value = description,
                        onValueChange = {
                            description = it
                        },
                        label = { Text("توضیحات", style = MaterialTheme.typography.bodyMedium) },
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth()
                            .height(100.dp),
                        textStyle = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                //Tag
                item {
                    Text("برچسب‌ها", style = MaterialTheme.typography.bodyMedium)
                    LazyRow(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(allTags) { tag ->
                            val isSelected = tags.contains(tag)

                            FilterChip(
                                selected = isSelected,
                                onClick = {
                                    Log.v("fatt", "hmmmm")
                                    tags = if (isSelected) {
                                        tags - tag
                                    } else {
                                        tags + tag
                                    }
                                    Log.v("fatt", "update tags: $tags")
                                },
                                label = { Text(tag.title) }
                            )
                        }
                    }

//                    var searchText by remember { mutableStateOf("") }
//                    val searchResults = remember { mutableStateListOf<String>() }
//
//                    OutlinedTextField(
//                        value = searchText,
//                        onValueChange = { searchText = it },
//                        label = { Text("برچسب", style = MaterialTheme.typography.bodyMedium) },
//                        textStyle = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrLtr),
//
//                        modifier = Modifier
//                            .padding(16.dp)
//                            .fillMaxWidth(),
//                        shape = RoundedCornerShape(12.dp),
//                    )
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        var expanded by remember { mutableStateOf(false) }
//                        SearchResultsList(searchResults = searchResults) { result ->
//                            if (!items.contains(result)) {
//                                items += result
//                            }
//                            searchText = result
//                            expanded = false
//                        }
//                    }

//                    searchResults.clear()
//                    for (item in items) {
//                        if (item.contains(searchText)) {
//                            searchResults.add(item)
//                        }
//                    }
                }

                // Date with Calendar Icon
                item {
                    val calendar = Calendar.getInstance()
                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                            dueDate = "$year-${month + 1}-$dayOfMonth"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = { Text("تاریخ", style = MaterialTheme.typography.bodyMedium) },
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth()
                            .clickable {
                                datePickerDialog.show()
                            },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                            disabledBorderColor = MaterialTheme.colorScheme.onBackground,
                            disabledTextColor = MaterialTheme.colorScheme.onBackground
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                datePickerDialog.show()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.DateRange,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "انتخاب تاریخ"
                                )
                            }
                        }
                    )
                }
                item {
                    val calendar = Calendar.getInstance()
                    val timePickerDialog = TimePickerDialog(
                        context,
                        { _: TimePicker, hourOfDay: Int, minute: Int ->
                            dueTime = "$hourOfDay:$minute"
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true
                    )
                    OutlinedTextField(
                        value = dueTime,
                        onValueChange = { dueTime = it },
                        label = { Text("زمان", style = MaterialTheme.typography.bodyMedium) },
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth()
                            .clickable {
                                timePickerDialog.show()
                            },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                            disabledBorderColor = MaterialTheme.colorScheme.onBackground,
                            disabledTextColor = MaterialTheme.colorScheme.onBackground
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                timePickerDialog.show()
                            }) {
                                Icon(
                                    imageVector = Icons.Default.AccessTime,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "انتخاب زمان"
                                )
                            }
                        }
                    )
                }
                val priorityOptions = listOf("مشخص نشده", "کم", "متوسط", "زیاد")
                // Priority Dropdown
                item {
                    // Dropdown to select priority
                    OutlinedTextField(
                        value = priorityOptions[selectedPriorityIndex],
                        onValueChange = { /* No action needed as it's read-only */ },
                        label = { Text("اولویت", style = MaterialTheme.typography.bodyMedium) },
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clickable {
                                selectedPriorityIndex =
                                    (selectedPriorityIndex + 1) % priorityOptions.size
                            },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground,
                            unfocusedLabelColor = MaterialTheme.colorScheme.primary,
                            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onBackground,
                            disabledBorderColor = MaterialTheme.colorScheme.onBackground,
                            disabledTextColor = MaterialTheme.colorScheme.onBackground
                        ),
                        trailingIcon = {
                            IconButton(onClick = {
                                // Toggle dropdown visibility
                                selectedPriorityIndex =
                                    (selectedPriorityIndex + 1) % priorityOptions.size
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Open priority dropdown"
                                )
                            }
                        }
                    )
                    priority = selectedPriorityIndex
                }

                item {
                    Button(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        onClick = {
                            if (title != "" && description != "" && dueDate != "") {
                                val task = Task(
                                    0,
                                    title,
                                    description,
                                    dueDate,
                                    false,
                                    priority,
                                    tags,
                                    null
                                )
                                Log.v("fatt", task.toString())
                                onAddTaskClicked(task)
                            } else {
                                snackBarVisible = true
                                snackBarMessage = "پر کردن همه موارد الزامی است."
                            }
                        }) {
                        Text(text = "افزودن وظیفه", style = MaterialTheme.typography.titleSmall, color = Color.White)
                    }
                }
            }
        }

        // Show snack bar
        if (snackBarVisible) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { snackBarVisible = false }) {
                        Text(
                            text = "بستن",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) {
                Text(
                    text = snackBarMessage,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun SearchResultsList(
    searchResults: List<String>,
    onItemClick: (String) -> Unit
) {
    searchResults.forEachIndexed { index, result ->
        Text(
            text = result, style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.clickable(onClick = {
                onItemClick(result)
            })
        )
        if (index == searchResults.lastIndex) {
            return@forEachIndexed
        }
    }
}