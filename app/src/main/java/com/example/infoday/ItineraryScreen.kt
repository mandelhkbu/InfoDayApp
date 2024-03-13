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
import kotlinx.coroutines.launch

@Composable
fun ItineraryScreen(snackbarHostState: SnackbarHostState) {

    val eventDao = EventDatabase.getInstance(LocalContext.current).eventDao()
    val events by eventDao.getAll().observeAsState(listOf())
    val coroutineScope = rememberCoroutineScope()

    LazyColumn {
        items(events.filter{ it.saved }) { event ->
            ListItem(
                headlineContent = { Text(event.title) },
                modifier = Modifier.pointerInput(Unit) {
                    detectTapGestures(
                        onLongPress = {
                            coroutineScope.launch {
                                event.saved = false
                                eventDao.update(event)
                                snackbarHostState.showSnackbar(
                                    "Event has been removed from itinerary."
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