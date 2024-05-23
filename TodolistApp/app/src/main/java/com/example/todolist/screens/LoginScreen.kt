package com.example.todolist.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.util.PatternsCompat
import com.example.todolist.R
import com.example.todolist.viewModel.UserViewModel


@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: UserViewModel,
    onLoginClicked: (String) -> Unit,
    onNavToSignupClicked: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var isEmailRequiredError by remember { mutableStateOf(false) }

    var password by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var isPasswordRequiredError by remember { mutableStateOf(false) }

    // Snack bar state
    var snackBarVisible by remember { mutableStateOf(false) }
    var snackBarMessage by remember { mutableStateOf("") }

    val token = viewModel.token
    LaunchedEffect(key1 = token) {
        token.collect {
            if (it != "") {
                onLoginClicked(it)
            }
        }
    }


    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyColumn(
            modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(R.drawable.carrot),
                        contentDescription = "Logo",
                        modifier = Modifier
                            .padding(16.dp)
                            .size(160.dp)
                            .align(Alignment.CenterHorizontally)
                    )

                    Text(
                        text = "ورود به حساب کاربری",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    OutlinedTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            isEmailRequiredError =
                                false // Reset the required error when the user starts typing again
                        },
                        label = { Text("پست الکترونیک") },
                        singleLine = true,
                        modifier = Modifier
                            .padding(16.dp, 2.dp)
                            .fillMaxWidth(),
                        textStyle = TextStyle(textDirection = TextDirection.ContentOrLtr),
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Mail, contentDescription = "Mail")
                        },
                        shape = RoundedCornerShape(12.dp),
                        supportingText = {
                            if (!PatternsCompat.EMAIL_ADDRESS.matcher(email)
                                    .matches() && email != ""
                            ) {
                                Text(text = "پست الکترونیک نادرست است.")
                            } else if (isEmailRequiredError) {
                                Text(text = "پست الکترونیک الزامی است.")
                            }
                        },
                        isError = (!PatternsCompat.EMAIL_ADDRESS.matcher(email)
                            .matches() && email != "") || isEmailRequiredError,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (email.isEmpty()) {
                                    isEmailRequiredError = true
                                }
                            }
                        )
                    )


                    OutlinedTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            isPasswordRequiredError =
                                false // Reset the required error when the user starts typing again
                        },
                        label = { Text("رمز عبور") },
                        singleLine = true,
                        modifier = Modifier
                            .padding(16.dp, 2.dp)
                            .fillMaxWidth(),
                        textStyle = TextStyle(textDirection = TextDirection.ContentOrLtr),
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = {
                            Icon(imageVector = Icons.Outlined.Lock, contentDescription = "Password")
                        },
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            // Please provide localized description for accessibility services
                            val description =
                                if (passwordVisible) "Hide password" else "Show password"

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, description)
                            }
                        },
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (password.isEmpty()) {
                                    isPasswordRequiredError = true
                                }
                            }
                        ),
                        supportingText = {
                            if (isPasswordRequiredError) {
                                Text(text = "رمز عبور الزامی است.")
                            }
                        },
                        isError = isPasswordRequiredError,
                    )
                }
            }

            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp, 4.dp)
                            .height(48.dp),
                        onClick = {
                            if (email != "" || password != "") {
                                viewModel.login(email, password)
                            } else {
                                snackBarVisible = true
                                snackBarMessage = "پر کردن همه موارد الزامی است."
                            }

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(
                            text = "ورود",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                }

                // button to navigate to the signup screen
                TextButton(onClick = {
                    onNavToSignupClicked()
                }) {
                    Text(text = "حساب کاربری ندارید؟ ثبت‌نام",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

            }
        }
        // Show snack bar
        if (snackBarVisible) {
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    TextButton(onClick = { snackBarVisible = false }) {
                        Text(text = "بستن")
                    }
                }
            ) {
                Text(text = snackBarMessage)
            }
        }
    }
}