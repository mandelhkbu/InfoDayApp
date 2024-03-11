package com.example.infoday

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.infoday.ui.theme.InfoDayTheme
import kotlinx.coroutines.launch

@Entity(tableName = "event")
data class Event(@PrimaryKey val id: Int, val title: String, val deptId: String, var saved: Boolean
) {

    companion object {
        val data = listOf(
            Event(id = 1, title = "Career Talks", deptId = "COMS", saved = false),
            Event(id = 2, title = "Guided Tour", deptId = "COMS", saved = true),
            Event(id = 3, title = "MindDrive Demo", deptId = "COMP", saved = false),
            Event(id = 4, title = "Project Demo", deptId = "COMP", saved = false)
        )
    }
}

//@Composable
//fun EventScreen() {
//    LazyColumn {
//        items(Event.data) { event ->
//            ListItem(
//                headlineContent = { Text(event.title) }
//            )
//            Divider()
//        }
//    }
//}

//@Composable
//fun EventScreen(deptId: String?) {
//    LazyColumn {
//        items(Event.data.filter { it.deptId == deptId }) { event ->
//            ListItem(
//                headlineContent = { Text(event.title) }
//            )
//            Divider()
//        }
//    }
//}


@Composable
fun EventScreen(snackbarHostState: SnackbarHostState, deptId: String?) {

    val eventDao = EventDatabase.getInstance(LocalContext.current).eventDao()
    val events by eventDao.getAll().observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        items(events.filter{it.deptId == deptId}) { event ->
//            ListItem(
//                headlineContent = { Text(event.title) }
//            )
            ListItem(
                headlineContent = { Text(event.title) },
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
//                            coroutineScope.launch {
//                                event.saved = true
//                                eventDao.update(event)
//                            }
                            coroutineScope.launch {
                                event.saved = true
                                eventDao.update(event)
                                snackbarHostState.showSnackbar(
                                    "Event has been added to itinerary."
                                )
                            }
                        }
                    )
                }
            )
            Divider()
        }
    }
}
//@Preview(showBackground = true)
//@Composable
//fun EventPreview() {
//    InfoDayTheme {
////        EventScreen()
//        EventScreen(snackbarHostState = snackbarHostState, deptId = "COMP")
//    }
//}