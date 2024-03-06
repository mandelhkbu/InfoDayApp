package com.example.infoday

import DeptScreen
import Feed
import FeedScreen
//import Feed
//import FeedScreen
import InfoScreen
import MapScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.infoday.ui.theme.InfoDayTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InfoDayTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
//                    Greeting("Android")
                    ScaffoldScreen()

                }
            }
        }
    }


}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    InfoDayTheme {
//        Greeting("Android")
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun InfoPreview() {
//    InfoDayTheme {
//        InfoGreeting()
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun InfoPreview() {
//    InfoDayTheme {
//        Column {
//            InfoGreeting()
//            PhoneList()
//        }
//    }
//}


//@Preview(showBackground = true)
//@Composable
//fun InfoPreview() {
//    InfoDayTheme {
//        InfoScreen()
//    }
//}

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun ScaffoldScreen() {
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("HKBU InfoDay App!") }
//            )
//        },
//        bottomBar = {},
//        content = { innerPadding ->
//            Column(
//                modifier = Modifier.padding(innerPadding),
//            ) {}
//        }
//
//    )
//}


@Preview(showBackground = true)
@Composable
fun InfoPreview() {
    InfoDayTheme {
        ScaffoldScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScaffoldScreen() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Events", "Itin", "Map", "Info")
    val navController = rememberNavController()

    val feeds by produceState(
        initialValue = listOf<Feed>(),
        producer = {
            value = KtorClient.getFeeds()
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HKBU InfoDay App!") }
            )
        },
        bottomBar = {
            NavigationBar {
            items.forEachIndexed { index, item ->
//                NavigationBarItem(
//                    icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
//                    label = { Text(item) },
//                    selected = selectedItem == index,
//                    onClick = { selectedItem = index }
//                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
                    label = { Text(item) },
                    selected = selectedItem == index,
                    onClick = {
                        selectedItem = index
                        navController.navigate(items[selectedItem]) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }},
        content = { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding),
            ) {
//                when (selectedItem) {
//                    0 -> InfoScreen(0)
//                    1 -> InfoScreen(1)
//                    2 -> InfoScreen(2)
////                    3 -> InfoScreen(3)
//                    3 -> MapScreen()
//                    4 -> DeptScreen()
//
//                }
//                NavHost(
//                    navController = navController,
//                    startDestination = "events",
//                ) {
//                    composable("events") { DeptScreen(navController) }
//                    composable("event") { EventScreen("COMP") }
//                    composable("event/{deptId}") { backStackEntry ->
//                        EventScreen(backStackEntry.arguments?.getString("deptId"))
//                    }
//                }
                NavHost(
                    navController = navController,
                    startDestination = "home",
                ) {
//                    composable("home") { DeptScreen(navController) }
                    composable("home") { FeedScreen(feeds) }

                    composable("events") { DeptScreen(navController) }
                    composable("event/{deptId}") { backStackEntry ->
                        EventScreen(backStackEntry.arguments?.getString("deptId"))
                    }
                    composable("itin") { InfoScreen(0) }
                    composable("map") { MapScreen() }
                    composable("info") { InfoScreen(1) }
                }
            }
        }

    )
}
