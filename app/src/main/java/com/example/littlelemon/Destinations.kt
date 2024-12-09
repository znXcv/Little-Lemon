package com.example.littlelemon

interface Destinations {
    val route: String
}

object OnboardingDes : Destinations {
    override val route = "Onboarding"
}

object HomeDes : Destinations {
    override val route = "Home"
}

object ProfileDes : Destinations {
    override val route = "Profile"
}