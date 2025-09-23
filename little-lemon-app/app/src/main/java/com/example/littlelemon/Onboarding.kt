package com.example.littlelemon


import android.content.Context
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.datastore.preferences.core.edit
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@Composable
fun Onboarding(navController: NavHostController) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val emailError = remember { mutableStateOf<String?>(null) }
    val ctx: Context = LocalContext.current

    Scaffold(
        topBar = {
            OnboardingTopAppBar()
        },
        bottomBar = {
            OnboardingBottomBar(firstName = firstName, lastName = lastName,
                email = email, emailError = emailError,
                navController = navController, ctx = ctx)
        }
    ){
        OnboardingContent(firstName, lastName, email, emailError, it)
    }
}

@Composable
fun OnboardingTopAppBar() {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()

    ) {
        Image(
            painter = painterResource(id = R.drawable.little_lemon_logo1),
            contentDescription = "Logo",
            modifier = Modifier.padding(vertical = 24.dp, horizontal = 80.dp)
        )
    }
}

@Composable
fun OnboardingContent(firstName: MutableState<String>,
            lastName: MutableState<String>,
            email: MutableState<String>,
            emailError: MutableState<String?>,
            paddingValues: PaddingValues){
    Column(
        modifier = Modifier.fillMaxSize().padding(paddingValues)
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF495E57))
                .padding(top = 50.dp, bottom = 50.dp),
            text = stringResource(id = R.string.let_s_get_to_know_you),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            color = Color(0xFFFFFFFF),
        )

        Column (
            modifier = Modifier.padding(start = 16.dp, top = 48.dp, end = 16.dp)
        ){
            Text(
                modifier = Modifier.padding( bottom = 48.dp),
                text = stringResource(id = R.string.personal_information),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = stringResource(R.string.first_name),
                fontSize = 16.sp,
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                value = firstName.value,
                onValueChange = { firstName.value = it },
                label = {
                    Text(stringResource(R.string.enter_your_first_name), style = TextStyle(color = Color.LightGray))
                },
            )

            Text(
                text = stringResource(R.string.last_name),
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 24.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                value = lastName.value,
                onValueChange = { lastName.value = it },
                label = {
                    Text(stringResource(R.string.enter_your_last_name), style = TextStyle(color = Color.LightGray))
                },

                )

            Text(
                text = "Email",
                fontSize = 16.sp,
                modifier = Modifier.padding(top = 24.dp)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                textStyle = TextStyle(fontSize = 16.sp),
                value = email.value,
                onValueChange = { newValue ->
                    email.value = newValue
                    emailError.value = validateEmail(email = newValue)
                },
                isError = emailError.value != null,
                supportingText = {
                    emailError.value?.let{Text(it, color = MaterialTheme.colorScheme.error)}
                },
                label = {
                    Text(stringResource(R.string.enter_your_email), style = TextStyle(color = Color.LightGray))
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
    }
}

@Composable
fun OnboardingBottomBar(
    firstName: MutableState<String>,
    lastName: MutableState<String>,
    email: MutableState<String>,
    emailError: MutableState<String?>,
    navController: NavHostController,
    ctx: Context
){
    val buttonCoroutineScope = rememberCoroutineScope()
    Button(
        onClick = {
            if(firstName.value.isBlank()){
                Toast.makeText(ctx, "First name cannot be empty", Toast.LENGTH_SHORT).show()
                return@Button
            }
            if(lastName.value.isBlank()){
                Toast.makeText(ctx, "Last name cannot be empty", Toast.LENGTH_SHORT).show()
                return@Button
            }
            emailError.value = validateEmail(email.value)
            if(firstName.value.isNotBlank() && lastName.value.isNotBlank()
                && emailError.value == null){
                buttonCoroutineScope.launch {
                    ctx.dataStore.edit { preferences ->
                        preferences[PreferencesKeys.FIRST_NAME] =  firstName.value
                        preferences[PreferencesKeys.LAST_NAME] =  lastName.value
                        preferences[PreferencesKeys.EMAIL] =  email.value
                    }
                }
                navController.navigate(Home.route)
            }
        },
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color(0xFF000000),
            containerColor = Color(0xFFF4CE14)
        ),
        modifier = Modifier.fillMaxWidth()
            .padding(start = 16.dp, bottom = 24.dp, end = 16.dp)
            .height(48.dp),
        ){
        Text(
            fontSize = 24.sp,
            text = stringResource(id = R.string.register),
        )
    }
}
/*

@Preview (showBackground = true)
@Composable
fun OnboardingPreview(){
    Onboarding(navController)
}*/
