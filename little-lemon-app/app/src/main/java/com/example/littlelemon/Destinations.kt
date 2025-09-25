package com.example.littlelemon

interface Destinations {
    var route: String
}

object Home: Destinations{
    override var route = "Home"
}

object Profile: Destinations{
    override var route = "Profile"
}

object Onboarding: Destinations{
    override var route = "Onboarding"
}

object DishDetails: Destinations{
    override var route = "DishDetails"
    const val argDishId = "dishId"
}