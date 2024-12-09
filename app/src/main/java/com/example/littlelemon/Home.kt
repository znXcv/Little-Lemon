package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.littlelemon.data.DatabaseRepository
import com.example.littlelemon.data.MenuItemLocal
import com.example.littlelemon.ui.theme.littleLemonColor
import com.example.littlelemon.ui.theme.onSecondaryLight
import com.example.littlelemon.ui.theme.secondaryLight
import com.example.littlelemon.ui.theme.surfaceContainerHighLight
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val databaseRepository by lazy { DatabaseRepository(context) }
    var menuItems by remember {
        mutableStateOf<List<MenuItemLocal>>(emptyList())
    }
    val searchContent = remember {
        mutableStateOf("")
    }
    LaunchedEffect(Unit) {
        scope.launch(Dispatchers.IO) {
            menuItems = databaseRepository.getAllMenuItems()
        }
    }
    LaunchedEffect(searchContent.value) {
        if (searchContent.value.isNotBlank()) {
            scope.launch(Dispatchers.IO) {
                menuItems = menuItems.filter {
                    it.title.contains(searchContent.value, ignoreCase = true)
                }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier.fillMaxSize()
    ) {
        //Header
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = R.drawable.little_lemon_logo),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .width(300.dp)
                    .height(40.dp)
                    .padding(start = 50.dp)
            )
            Box {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(16.dp)
                        .clip(CircleShape)
                        .clickable {
                            navController.navigate(ProfileDes.route)
                        }
                )
            }
        }

        // hero banner
        Surface(
            color = secondaryLight,
            contentColor = onSecondaryLight,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.app_name),
                        style = MaterialTheme.typography.titleLarge,
                        color = littleLemonColor,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = stringResource(R.string.city_name),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.short_description),
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.width(220.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "little lemon hero",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .height(130.dp)
                            .width(130.dp)
                            .clip(RoundedCornerShape(24.dp))
                    )
                }

                CustomSearchBar(
                    modifier = Modifier.fillMaxWidth(),
                    onSearched = {
                        searchContent.value = it
                    }
                )
            }

        }

        //Delivery categories
        Row {
            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.order_for_delivery),
                    modifier = Modifier.padding(start = 16.dp),
                    style = MaterialTheme.typography.titleSmall
                )
                Row {
                    val deliveryCategories = listOf(
                        "Starters", "Mains", "Desserts", "Drinks"
                    )
                    LazyRow(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        items(deliveryCategories) { menu ->
                            Card(
                                onClick = { /*TODO*/ },
                                modifier = Modifier
                                    .height(34.dp)
                                    .width(80.dp)
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    Text(
                                        text = menu,
                                        color = secondaryLight,
                                        style = MaterialTheme.typography.labelLarge,
                                        modifier = Modifier
                                            .clickable {
                                                scope.launch(Dispatchers.IO) {
                                                    menuItems = menuItems.filter {
                                                        it.category.contains(
                                                            menu,
                                                            ignoreCase = true
                                                        )
                                                    }
                                                }
                                            }
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }


        HorizontalDivider(modifier = Modifier.padding(16.dp))

        //Menu items
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            val menus = menuItems
            items(menus) {
                MenuItemCard(
                    menuItem = it,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                HorizontalDivider(modifier = Modifier.padding(16.dp))
            }
        }


    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun MenuItemCard(
    menuItem: MenuItemLocal,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = menuItem.title,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = menuItem.description,
                modifier = Modifier.width(260.dp),
                style = MaterialTheme.typography.bodyMedium,
                color = secondaryLight
            )
            Text(text = "$${menuItem.price.toFloat()}")
        }

        Box {
            GlideImage(
                model = menuItem.image,
                contentDescription = menuItem.title,
                modifier = Modifier.size(100.dp)
            )
        }
    }
}

@Composable
fun CustomSearchBar(modifier: Modifier = Modifier, onSearched: (String) -> Unit) {
    var text by remember { mutableStateOf("") }

    OutlinedTextField(
        value = text,
        onValueChange = { newText ->
            text = newText
            onSearched(newText)
        },
        placeholder = {
            Text(text = "Search")
        },
        leadingIcon = {
            Icon(imageVector = Icons.Default.Search, contentDescription = null)
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                // Handle search action here
                onSearched(text)
            }
        ),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedContainerColor = surfaceContainerHighLight,
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun HomeScreePreview() {
    HomeScreen(
        navController = NavHostController(LocalContext.current)
    )
}

@Preview(showBackground = true)
@Composable
fun MenuItemCardPreview() {
    MenuItemCard(
        menuItem = MenuItemLocal(
            id = 1,
            title = "Greek Salad",
            description = "The famous greek salad of crispy lettuce, peppers, olives, our Chicago...",
            price = "$12.99",
            image = "",
            category = ""
        )
    )
}