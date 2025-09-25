package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.room.Room
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

//@Preview(showBackground = true)
@Composable
fun DishDetails(navController: NavHostController, id: Int) {
    Scaffold(
        topBar = {
            DishDetailsTopAppBar(navController)
        }
    ) {
        DishContent(it, id)
    }
}

@Composable
fun DishDetailsTopAppBar(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)
    ) {
        IconButton(
            onClick = {
                navController.popBackStack()
            }
        ){
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back")
        }
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
                    modifier = Modifier.size(60.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DishContent(paddingValues: PaddingValues, id: Int) {
    val ctx = LocalContext.current
    val database by lazy{
        Room.databaseBuilder(context = ctx, klass = AppDatabase::class.java, name ="menuDatabase").build()
    }
    val databaseMenu by database.menuDao().getAll().observeAsState(emptyList<MenuItemRoom>())
    val dish = databaseMenu.firstOrNull{ it.id == id}
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(paddingValues),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            GlideImage(
                model = dish?.image,
                contentDescription = dish?.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1F),
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 24.dp, horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = dish?.title.toString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
                )
            Text(
                text = "$${dish?.price.toString()}",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = dish?.description.toString(),
            fontSize = 16.sp,
            )
    }
}