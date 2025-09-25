package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.map

@Composable
fun NavigationComposable(navController: NavHostController, databaseMenu: List<MenuItemRoom>){
    val ctx: Context = LocalContext.current

    val email by ctx.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.EMAIL]?: ""
    }.collectAsStateWithLifecycle(initialValue = "")

    val startDestination = if(email.isEmpty()) Onboarding.route else Home.route

    NavHost(navController = navController, startDestination = startDestination){
        composable(Home.route){
            Home(navController, databaseMenu)
        }
        composable(Profile.route) {
            Profile(navController)
        }
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(
            DishDetails.route + "/{${DishDetails.argDishId}}",
            arguments = listOf(navArgument(DishDetails.argDishId){
                type = NavType.IntType
            })
        ){
            val id = requireNotNull(it.arguments?.getInt(DishDetails.argDishId)) {"Dish id is null"}
            DishDetails(navController, id = id)
        }
    }
}