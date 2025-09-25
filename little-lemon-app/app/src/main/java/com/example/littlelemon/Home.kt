package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage


//@Preview(showBackground = true)
@Composable
fun Home(navController: NavHostController, databaseMenu: List<MenuItemRoom>) {
//fun Home() {
    Scaffold (
        topBar = {
            HomeTopAppBar(navController)
        },
    ){
        HomeContent(it, navController, databaseMenu)
    }
}

@Composable
fun HomeTopAppBar(navController: NavHostController) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(vertical = 32.dp)
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
                    modifier = Modifier.size(60.dp),
                )
            }
        }
    }
}

@Composable
fun HomeContent(
    values: PaddingValues,
    navController: NavHostController,
    databaseMenu: List<MenuItemRoom>
) {
    var menuItems: List<MenuItemRoom>
    Column(
        modifier = Modifier.padding(values)
    ) {
        var searchQuery by remember{
            mutableStateOf("")
        }
        var starterFlag by remember{
            mutableStateOf(false)
        }
        var dessertsFlag by remember{
            mutableStateOf(false)
        }
        var mainsFlag by remember{
            mutableStateOf(false)
        }
        var drinksFlag by remember{
            mutableStateOf(false)
        }

        val focusManager = LocalFocusManager.current
        Column(
            modifier = Modifier
                .background(color = Color(0xFF495E57))
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .pointerInput(Unit){
                    detectTapGestures( onTap = {
                        focusManager.clearFocus()
                    })
                },
        ) {
            Text(
                text = stringResource(R.string.little_lemon),
                color = Color(0xFFF4CE14),
                fontWeight = FontWeight.Bold,
                fontSize = 32.sp,
            )
            Row(
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier = Modifier.fillMaxWidth(0.7F)
                ) {
                    Text(
                        text = stringResource(R.string.chicago),
                        color = Color(0xFFFFFFFF),
                        fontSize = 24.sp,
                    )
                    Text(
                        text = stringResource(R.string.hero_message),
                        color = Color(0xFFFFFFFF),
                        fontSize = 16.sp,
                        modifier = Modifier.fillMaxWidth()
                            .padding(end = 16.dp, top = 16.dp)
                    )
                }
                Column(
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.heroimage),
                        contentDescription = "Hero Image",
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .aspectRatio(1F),
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            TextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    },
                modifier = Modifier.fillMaxWidth()
                    .padding(bottom = 8.dp, top = 16.dp),
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search Icon") },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { searchQuery = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear search")
                        }
                    }
                },
                placeholder = {Text("Enter search phrase....",)},
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFFFFFFF),
                    focusedContainerColor = Color(0xFFFFFFFF)
                ),
                shape = RoundedCornerShape(16.dp),
            )
        }
        menuItems =
            if(searchQuery.isNotEmpty()) databaseMenu.filter {
                it.title.contains(searchQuery, ignoreCase = true)
            }else if(starterFlag) databaseMenu.filter {
                it.category == "starters"
            }else if(mainsFlag) databaseMenu.filter {
                it.category == "mains"
            }else if(dessertsFlag) databaseMenu.filter {
                it.category == "desserts"
            }else if(drinksFlag) databaseMenu.filter {
                it.category == "drinks"
            } else databaseMenu

        Column(
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                modifier = Modifier.padding(vertical = 8.dp),
                text = stringResource(R.string.order_for_delivery),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ){
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDEFEE),
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        starterFlag = true
                        dessertsFlag = false
                        mainsFlag = false
                        drinksFlag = false
                        searchQuery = ""
                    }){
                    Text(
                        text = stringResource(R.string.starters),
                        color = Color(0xFF000000),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDEFEE),
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        starterFlag = false
                        dessertsFlag = false
                        mainsFlag = true
                        drinksFlag = false
                        searchQuery = ""
                    }){
                    Text(
                        text = stringResource(R.string.mains),
                        color = Color(0xFF000000),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDEFEE),
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        starterFlag = false
                        dessertsFlag = true
                        mainsFlag = false
                        drinksFlag = false
                        searchQuery = ""
                    }){
                    Text(
                        text = stringResource(R.string.desserts),
                        color = Color(0xFF000000),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEDEFEE),
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        starterFlag = false
                        dessertsFlag = false
                        mainsFlag = false
                        drinksFlag = true
                        searchQuery = ""
                    }){
                    Text(
                        text = stringResource(R.string.drinks),
                        color = Color(0xFF000000),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                thickness = 2.dp,
                color = Color(0xFFEDEFEE)
            )
            LazyColumn {
                itemsIndexed(items = menuItems){_, dish ->
                    MenuDish(dish, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuDish(dish: MenuItemRoom, navController: NavHostController) {
    Card(
        onClick = {
            navController.navigate(DishDetails.route + "/${dish.id}")
        }
    ){
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            Column(
                modifier = Modifier.fillMaxWidth(0.7F)

            ) {
                Text(
                    text = dish.title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Text(
                    text = dish.description,
                    fontSize = 16.sp,
                )
                Text(
                    text = "$${dish.price.toString()}",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            GlideImage(
                model = dish.image,
                contentDescription = dish.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1F),
            )
        }
    }
    HorizontalDivider(
        modifier = Modifier.padding(vertical = 2.dp),
        thickness = 2.dp,
        color = Color(0xFFEDEFEE)
    )
}
