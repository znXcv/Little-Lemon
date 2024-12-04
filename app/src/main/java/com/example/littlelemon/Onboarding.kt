package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.ui.theme.HomeDes
import com.example.littlelemon.ui.theme.LemonTitleColor
import com.example.littlelemon.ui.theme.LittleLemonColor
import kotlinx.coroutines.launch

@Composable
fun Onboarding(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("little_lemon", Context.MODE_PRIVATE)
    val coroutineScope = rememberCoroutineScope()

    val firstNameValue = remember { mutableStateOf("") }
    val lastNameValue = remember { mutableStateOf("") }
    val emailValue = remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = painterResource(id = R.drawable.little_lemon_logo),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(top = 16.dp, bottom = 16.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = LemonTitleColor)
                .height(100.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Let's get to know you",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(),
                style = TextStyle(
                    fontSize = 24.sp
                )
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Personal information",
                style = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(start = 16.dp, top = 40.dp)
            )

            Spacer(modifier = Modifier.weight(0.5f))

            Column(
                verticalArrangement = Arrangement.spacedBy(40.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Column {
                    Text(text = "First name") // Top label
                    Spacer(modifier = Modifier.height(8.dp)) // Add some space between labels and fields
                    OutlinedTextField(
                        value = firstNameValue.value,
                        onValueChange = {
                            firstNameValue.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Required")
                        },
                    )
                }

                Column {
                    Text(text = "Last name") // Top label
                    Spacer(modifier = Modifier.height(8.dp)) // Add some space between labels and fields
                    OutlinedTextField(
                        value = lastNameValue.value,
                        onValueChange = {
                            lastNameValue.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Required")
                        },
                    )
                }

                Column {
                    Text(text = "Email") // Top label
                    Spacer(modifier = Modifier.height(8.dp)) // Add some space between labels and fields
                    OutlinedTextField(
                        value = emailValue.value,
                        onValueChange = {
                            emailValue.value = it
                        },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = {
                            Text(text = "Required")
                        },
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    coroutineScope.launch {
                        if (firstNameValue.value.isNotEmpty() && lastNameValue.value.isNotEmpty() && emailValue.value.isNotEmpty()) {
                            sharedPreferences.edit()
                                .putString("firstName", firstNameValue.value)
                                .putString("lastName", lastNameValue.value)
                                .putString("email", emailValue.value)
                                .putString("register", "Registration successful!")
                                .putBoolean("isLoggedIn", true)
                                .apply()
                        } else {
                            sharedPreferences.edit()
                                .putString(
                                    "register",
                                    "Registration unsuccessful. Please enter all data."
                                )
                                .putBoolean("isLoggedIn", false)
                                .apply()
                        }
                        navController.navigate(HomeDes.route)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 34.dp)
            ) {
                Text(text = "Register")
            }

        }

    }
}


@Preview(showSystemUi = true, showBackground = true)
@Composable
fun OnboardingPreview() {
    Onboarding(navController = NavHostController(LocalContext.current))
}