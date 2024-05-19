package com.example.todolist.screens

import android.app.DatePickerDialog
import android.app.TimePickerDialog
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.todolist.models.Tag
import com.example.todolist.models.Task
import java.util.Calendar

var items = listOf("برچسب ۱", "برچسب ۲", "برچسب ۳")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddScreen(
    modifier: Modifier = Modifier,
    onCancelClicked: () -> Unit,
    onAddTaskClicked: (task:Task) -> Unit
) {

    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var dueDate by remember { mutableStateOf("") }
    var dueTime by remember { mutableStateOf("") }
    var priority by remember { mutableIntStateOf(0) }
    var tags by remember { mutableStateOf<List<Tag>>(emptyList()) }
    var selectedPriorityIndex by remember { mutableStateOf(0) }
    val context = LocalContext.current
    var Priority = 0

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "کارات",
                            style = MaterialTheme.typography.titleLarge
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
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        val task = Task(1,
                            "تست",
                            "تست توضیح",
                            "2021-12-13",
                            false,
                            1,
                            listOf(),
                            1)
                        onAddTaskClicked(task)
                    },
                    containerColor = MaterialTheme.colorScheme.primary
                ) {
                    Icon(Icons.Default.Check, contentDescription = "Add")
                }
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
                        label = { Text("عنوان") },
                        singleLine = true,
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth(),
                        textStyle = TextStyle(textDirection = TextDirection.ContentOrLtr),
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
                        label = { Text("توضیحات") },
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth()
                            .height(100.dp),
                        textStyle = TextStyle(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                    )
                }
                //Tag
                item {

                    var searchText by remember { mutableStateOf("") }
                    val searchResults = remember { mutableStateListOf<String>() }

                    OutlinedTextField(
                        value = searchText,
                        onValueChange = { searchText = it },
                        label = { Text("برچسب") },
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
//                        trailingIcon = {
//                            IconButton(onClick = {
//
//                            }) {
//                                Icon(imageVector = Icons.Default.Circle,
//                                    tint = MaterialTheme.colorScheme.primary,contentDescription = "انتخاب تاریخ")
//                            }
//                        },
                        shape = RoundedCornerShape(12.dp),
                    )
                    Column(modifier = Modifier.padding(16.dp)) {
                        var expanded by remember { mutableStateOf(false) }
                        SearchResultsList(searchResults = searchResults) { result ->
                            if (!items.contains(result)) {
                                items += result
                            }
                            searchText = result
                            expanded = false
                        }
                    }

                    searchResults.clear()
                    for (item in items) {
                        if (item.contains(searchText)) {
                            searchResults.add(item)
                        }
                    }
                }

                // Date with Calendar Icon
                item {
                    val calendar = Calendar.getInstance()
                    val datePickerDialog = DatePickerDialog(
                        context,
                        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                            dueDate = "$dayOfMonth/${month + 1}/$year"
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )
                    OutlinedTextField(
                        value = dueDate,
                        onValueChange = { dueDate = it },
                        label = { Text("تاریخ") },

                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth()
                            .clickable {
                                datePickerDialog.show()
                            },
                        textStyle = TextStyle(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        enabled = false,

                        trailingIcon = {
                            IconButton(onClick = {
                                datePickerDialog.show()
                            }) {
                                Icon(imageVector = Icons.Default.DateRange,
                                    tint = MaterialTheme.colorScheme.primary,contentDescription = "انتخاب تاریخ")
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
                        label = { Text("زمان") },
                        modifier = Modifier
                            .padding(16.dp, 8.dp)
                            .fillMaxWidth()
                            .clickable {
                                timePickerDialog.show()
                            },
                        textStyle = TextStyle(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                        enabled = false,
                        trailingIcon = {
                            IconButton(onClick = {
                                timePickerDialog.show()
                            }) {
                                Icon(imageVector = Icons.Default.AccessTime,
                                    tint = MaterialTheme.colorScheme.primary,contentDescription = "انتخاب زمان")
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
                        label = { Text("اولویت") },
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        enabled = false,
                        trailingIcon = {
                            IconButton(onClick = {
                                // Toggle dropdown visibility
                                selectedPriorityIndex = (selectedPriorityIndex + 1) % priorityOptions.size
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    tint = MaterialTheme.colorScheme.primary,
                                    contentDescription = "Open priority dropdown"
                                )
                            }
                        }
                    )
                    Priority = selectedPriorityIndex
                }




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
            text = result,
            modifier = Modifier.clickable(onClick = {
                onItemClick(result)
            })
        )
        if (index == searchResults.lastIndex) {
            return@forEachIndexed
        }
    }
}