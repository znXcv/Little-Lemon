package com.example.littlelemon.ui.theme

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.littlelemon.R

@Composable
fun Profile(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("little_lemon", Context.MODE_PRIVATE)
    val firstName = sharedPreferences.getString("firstName", "") ?: ""
    val lastName = sharedPreferences.getString("lastName", "") ?: ""
    val email = sharedPreferences.getString("email", "") ?: ""

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

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(top = 16.dp)
        ) {
            Text(
                text = "Personal information:",
                style = TextStyle(
                    fontSize = 20.sp
                ),
                modifier = Modifier.padding(top = 40.dp, start = 14.dp)
            )

            Spacer(modifier = Modifier.weight(0.3f))

            Column(
                verticalArrangement = Arrangement.spacedBy(40.dp),
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .padding(14.dp)
            ) {
                Column {
                    Text(text = "First name") // Top label
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = firstName,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }

                Column {
                    Text(text = "Last name") // Top label
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = lastName,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier
                                .padding(16.dp)

                        )
                    }
                }

                Column {
                    Text(text = "Email") // Top label
                    Spacer(modifier = Modifier.height(16.dp))
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = email,
                            style = TextStyle(fontSize = 16.sp),
                            modifier = Modifier
                                .padding(16.dp)

                        )
                    }
                }

            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    sharedPreferences.edit()
                        .clear()
                        .apply()
                    navHostController.navigate(OnboardingDes.route)
                },
                colors = ButtonDefaults.buttonColors(containerColor = LittleLemonColor),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 34.dp)
            ) {
                Text(text = "Log Out")
            }

        }

    }

}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun ProfilePreview() {
    Profile(navHostController = NavHostController(LocalContext.current))
}