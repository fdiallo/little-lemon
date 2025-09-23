package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.map

@Composable
fun NavigationComposable(navController: NavHostController, ctx: Context){
    val ctx: Context = LocalContext.current
    val firstName by ctx.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.FIRST_NAME]?: ""
    }.collectAsStateWithLifecycle(initialValue = "")

    val lastName by ctx.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.LAST_NAME]?: ""
    }.collectAsStateWithLifecycle(initialValue = "")

    val email by ctx.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.EMAIL]?: ""
    }.collectAsStateWithLifecycle(initialValue = "")

    val startDestination = if(email.isEmpty()) Onboarding.route else Home.route

    NavHost(navController = navController, startDestination = startDestination){
        composable(Home.route){
            Home(navController)
        }
        composable(Profile.route) {
            Profile(navController)
        }
        composable(Onboarding.route) {
            Onboarding(navController)
        }
    }

}