package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

//@Preview(showBackground = true)
@Composable
fun Home(navController: NavHostController) {
    Scaffold (
        topBar = {
            HomeTopAppBar(navController)
       },
        bottomBar = {
            HomeBottomAppBar()
        }

    ){
            HomeContent(it)
        }
}


@Composable
fun HomeTopAppBar(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)

    ) {
        Image(
            painter = painterResource(R.drawable.little_lemon_logo1),
            contentDescription = "Logo",
            modifier = Modifier.padding(start = 38.dp, end = 0.dp)
                .width(300.dp).height(58.dp)
        )
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth(),
        ) {
            IconButton(
                onClick = {
                    navController.navigate(Profile.route)
                },
            ) {
                Image(
                    painter = painterResource(R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.size(60.dp)
                )
            }
        }
    }
}

@Composable
fun HomeContent(values: PaddingValues) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(values)
    ) {
        Text(text = "Home", modifier = Modifier.padding(values))

    }

}

@Composable
fun HomeBottomAppBar(){

}