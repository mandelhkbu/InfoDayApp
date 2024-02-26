import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.infoday.R
import com.example.infoday.ui.theme.InfoDayTheme

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
fun InfoGreeting(id: Int) {
    val padding = 16.dp
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(Modifier.size(padding))
        Image(
            painter = painterResource(id = R.drawable.hkbu_logo),
            contentDescription = stringResource(id = R.string.hkbu_logo),
            modifier = Modifier.size(180.dp)
        )
        Spacer(Modifier.size(padding))
        Text(text = "HKBU InfoDay App $id", fontSize = 30.sp)
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
                trailingContent = { Text(message.tel) }
            )
        }
    }
}

@Composable
fun InfoScreen(id: Int) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        InfoGreeting(id)
        PhoneList()
    }
}

