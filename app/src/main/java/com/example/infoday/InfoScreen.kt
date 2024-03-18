import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infoday.R
import com.example.infoday.ui.theme.InfoDayTheme
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

//@Composable
//fun InfoGreeting() {
//    Image(
//        painter = painterResource(id = R.drawable.hkbu_logo),
//        contentDescription = stringResource(id = R.string.hkbu_logo),
//    )
//    Text(text = "Hello Android!")
//}

//@Composable
//fun InfoGreeting() {
//    Column {
//        Image(
//            painter = painterResource(id = R.drawable.hkbu_logo),
//            contentDescription = stringResource(id = R.string.hkbu_logo),
//        )
//        Text(text = "Hello Android!")
//    }
//}

@Composable
fun InfoGreeting() {
    val padding = 16.dp
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.size(padding))
        Image(
            painter = painterResource(id = R.drawable.hkbu_logo),
            contentDescription = stringResource(id = R.string.hkbu_logo),
            modifier = Modifier.size(180.dp)
        )
        Spacer(Modifier.size(padding))
        Text(text = "HKBU InfoDay App", fontSize = 30.sp)
    }
}

data class Contact(val office: String, val tel: String) {
    companion object {
        val data = listOf(
            Contact(office = "Admission Office", tel = "3411-2200"),
            Contact(office = "Emergencies", tel = "3411-7777"),
            Contact(office = "Health Services Center", tel = "3411-7447")
        )
    }
}

//@Composable
//fun PhoneList() {
//    Column {
//        Contact.data.forEach { message ->
//            Text(message.office)
//        }
//    }
//}

@Composable
fun PhoneList() {
    val ctx = LocalContext.current


    Column {
        Contact.data.forEach { message ->
            ListItem(
                headlineContent = { Text(message.office) },
                leadingContent = {
                    Icon(
                        Icons.Filled.Call,
                        contentDescription = null
                    )
                },
                trailingContent = { Text(message.tel) },
                modifier = Modifier.clickable {
                    val u = Uri.parse("tel:" + message.tel)
                    val i = Intent(Intent.ACTION_DIAL, u)
                    ctx.startActivity(i)
                },
            )
        }
    }
}

@Composable
fun InfoScreen(snackbarHostState: SnackbarHostState) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InfoGreeting()
        PhoneList()
        SettingList()
        FeedBack(snackbarHostState)
    }
}

//@Composable
//fun SettingList() {
//    val dataStore = UserPreferences(LocalContext.current)
//    val coroutineScope = rememberCoroutineScope()
////    var checked by remember { mutableStateOf(true) }
//
//    val checked by dataStore.getMode.collectAsState(initial = false)
//
//    ListItem(
//        headlineContent = { Text("Dark Mode") },
//        leadingContent = {
//            Icon(
//                Icons.Filled.Settings,
//                contentDescription = null
//            )
//        },
//        trailingContent = {
////            Switch(
////                modifier = Modifier.semantics { contentDescription = "Demo" },
////                checked = checked,
////                onCheckedChange = {
////                    checked = it
////                    coroutineScope.launch {
////                        dataStore.saveMode(it)
////                    }
////                })
//            Switch(
//                modifier = Modifier.semantics { contentDescription = "Demo" },
////                checked = checked,
//                checked = checked ?: true,
//                onCheckedChange = {
////                    checked = it
//                    coroutineScope.launch {
//                        dataStore.saveMode(it)
//                    }
//                })
//        }
//    )
//}

@Composable
fun SettingList() {

    var checked by remember { mutableStateOf(true) }
    val userPreferences = UserPreferences.getInstance(LocalContext.current.dataStore)
    val coroutineScope = rememberCoroutineScope()

    ListItem(
        headlineContent = { Text("Dark Mode") },
        leadingContent = {
            Icon(
                Icons.Filled.Settings,
                contentDescription = null
            )
        },
        trailingContent = {
            Switch(
                modifier = Modifier.semantics { contentDescription = "Demo" },
                checked = checked,
                onCheckedChange = {
                    checked = it
                })
        }
    )
}

@Composable
fun FeedBack(snackbarHostState: SnackbarHostState) {
    val padding = 16.dp
    var message by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(
            maxLines = 1,
            value = message,
            onValueChange = { message = it }
        )
        Spacer(Modifier.size(padding))

        Button(onClick = {
            coroutineScope.launch {
                val stringBody: String = KtorClient.postFeedback(message)
                snackbarHostState.showSnackbar(stringBody)
            }
        }) {
            Text(text = "Submit feedback")
        }
    }
}

@Serializable
data class HttpBinResponse(
    val args: Map<String, String>,
    val data: String,
    val files: Map<String, String>,
    val form: Map<String, String>,
    val headers: Map<String, String>,
    val json: String?,
    val origin: String,
    val url: String
)