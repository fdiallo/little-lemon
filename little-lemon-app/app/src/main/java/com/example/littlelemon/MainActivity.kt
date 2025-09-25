package com.example.littlelemon

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
   private val client = HttpClient(Android){
        install(ContentNegotiation){
            json(contentType = ContentType(contentType = "text", contentSubtype = "plain"))
        }
    }

    private val database by lazy{
        Room.databaseBuilder(context = applicationContext, klass = AppDatabase::class.java, name ="menuDatabase").build()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        setContent {

            LittleLemonTheme {
                val navController = rememberNavController()
                val databaseMenu by database.menuDao().getAll().observeAsState(emptyList<MenuItemRoom>())
                NavigationComposable(navController, databaseMenu)
            }
        }
        lifecycleScope.launch(Dispatchers.IO) {
            if(database.menuDao().isEmpty()) {
                saveMenuToDatabase(fetchMenu())
            }
        }
    }

    private fun saveMenuToDatabase(menuNetwork: List<MenuItemNetwork>){
        val menuRoom = menuNetwork.map { it.toMenuItemRoom() }
        database.menuDao().insertAll(*menuRoom.toTypedArray())
    }

    private suspend fun fetchMenu(): List<MenuItemNetwork>{
        val url = "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"
        val response = client.get(url).body<MenuNetwork>().menu
        return response
    }

}