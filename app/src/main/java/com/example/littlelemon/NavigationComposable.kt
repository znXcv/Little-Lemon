package com.example.littlelemon

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun MyNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = OnboardingDes.route) {
        composable(OnboardingDes.route) {
            Onboarding(
                navController = navController,
                modifier = modifier
            )
        }


        composable(ProfileDes.route) {
            Profile(
                navHostController = navController,
                modifier = modifier
            )
        }
    }
}