import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.infoday.ui.theme.InfoDayTheme

data class Feed(val id: Int, val image: String, val title: String, val detail: String) {
    companion object {
        val data = listOf(
            Feed(
                123, "https://cdn.stocksnap.io/img-thumbs/960w/philadelphia-travel_LPDQBLM2A0.jpg", " COMP ", " discount "
            ),
        )

    }
}


@Composable
fun FeedScreen(feeds: List<Feed>) {

    LazyColumn {
        items(feeds) { feed ->
            Text(feed.title)
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FeedPreview() {
    val feeds by produceState(
        initialValue = listOf<Feed>(),
        producer = {
            value = KtorClient.getFeeds()
        }
    )
    InfoDayTheme {
//        FeedScreen(Feed.data)
        FeedScreen(feeds)
    }
}

