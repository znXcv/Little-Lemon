package com.example.littlelemon

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.littlelemon.data.DatabaseRepository
import com.example.littlelemon.data.MenuNetworkData
import com.example.littlelemon.data.toMenuItemLocal
import com.example.littlelemon.ui.theme.LittleLemonTheme
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    private val httpClient = HttpClient {
        install(ContentNegotiation) {
            json(contentType = ContentType("text", "plain"))
        }
    }

    private val databaseRepository by lazy { DatabaseRepository(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LittleLemonTheme {
                val menuItems = remember {
                    mutableStateOf<MenuNetworkData?>(null)
                }
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LaunchedEffect(Unit) {
                        try {
                            withContext(Dispatchers.IO) {
                                val menus: HttpResponse = httpClient
                                    .get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json")
                                Log.d("Menu", menus.status.toString())
                                menuItems.value = menus.body<MenuNetworkData>()
                                menuItems.value?.menu?.forEach {
                                    databaseRepository.insertMenuItems(menuItems = it.toMenuItemLocal())
                                }
                            }
                        } catch (e: Exception) {
                            Log.e("Error", e.message.toString())
                        }
                    }
                    MyNavigation(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
