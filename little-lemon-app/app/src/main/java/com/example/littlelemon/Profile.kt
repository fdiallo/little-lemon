package com.example.littlelemon

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

//@Preview(showBackground = true)
@Composable
fun Profile(navController: NavHostController) {
    val ctx: Context = LocalContext.current
    val profileCoroutineScope = rememberCoroutineScope()
    Scaffold(
        topBar = {
            ProfileTopAppBar(navController)
        },
        bottomBar = {
            ProfileBottomAppBar(navController, profileCoroutineScope, ctx,)
        }
    ) {
        ProfileContent(paddingValues = it)
    }
}

@Composable
fun ProfileTopAppBar(navController: NavHostController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
            navController.popBackStack()
        }){
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back")
        }
        Image(
            painter = painterResource(id = R.drawable.little_lemon_logo1),
            contentDescription = "Logo",
            modifier = Modifier.padding(horizontal = 80.dp, vertical = 32.dp)
        )
    }
}

@Composable
fun ProfileContent(paddingValues: PaddingValues){
    val ctx: Context = LocalContext.current
    val firstName by ctx.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.FIRST_NAME]?:""
    }.collectAsStateWithLifecycle(initialValue = "")

    val lastName by ctx.dataStore.data.map {preferences ->
        preferences[PreferencesKeys.LAST_NAME]?:""
    }.collectAsStateWithLifecycle(initialValue = "")

    val email by ctx.dataStore.data.map {preferences ->
        preferences[PreferencesKeys.EMAIL]?: ""
    }.collectAsStateWithLifecycle(initialValue = "")

    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        Text(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 48.dp),
            text = stringResource(R.string.profile_information),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF000000))

        Column(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 48.dp )
        ) {
            Row (
                modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ){
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile Picture",
                )
            }
            Text(text = stringResource(R.string.first_name), fontSize = 16.sp)
            OutlinedTextField(
                value = firstName,
                onValueChange = { /** Not editable */ },
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                )

            Text(text = stringResource(R.string.last_name), fontSize = 16.sp,
                modifier = Modifier.padding(top = 24.dp))
            OutlinedTextField(
                value = lastName,
                onValueChange = { /** Not editable */ },
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp)
            )

            Text(text = stringResource(R.string.email), fontSize = 16.sp,
                modifier = Modifier.padding(top = 24.dp))
            OutlinedTextField(
                value = email,
                onValueChange = { /** Not editable */ },
                readOnly = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }
    }
}

@Composable
fun ProfileBottomAppBar(
    navController: NavHostController,
    profileCoroutineScope: CoroutineScope,
    ctx: Context
) {
    Button(
        onClick = {
            profileCoroutineScope.launch { profileCoroutineScope
                ctx.dataStore.edit { preferences ->
                    preferences[PreferencesKeys.FIRST_NAME] = ""
                    preferences[PreferencesKeys.LAST_NAME] = ""
                    preferences[PreferencesKeys.EMAIL] = ""
                }
            }
            navController.navigate(Onboarding.route)
        },
        modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF000000),
            containerColor = Color(0xFFF4CE14)
        ),
        shape = RoundedCornerShape(16.dp)
    ){
        Text(text = "Log out", fontSize = 16.sp)
    }
}

