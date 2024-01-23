import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.usecase.picture_selector.Media
import com.usecase.picture_selector.PictureSelectParams
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    val scope = rememberCoroutineScope()
    Scaffold(modifier = Modifier.statusBarsPadding()) {
        var pictureMedia by remember { mutableStateOf<Media?>(null) }
        Column {
            Button(onClick = {
                scope.launch {
                    pictureMedia =
                        pictureSelect.takePhoto(params = PictureSelectParams(1)).firstOrNull()
                            ?.getOrNull()?.firstOrNull()
                }
            }) {
                Text("拍照", modifier = Modifier.padding(24.dp).fillMaxWidth())
            }
            Button(onClick = {
                scope.launch {
                    pictureMedia =
                        pictureSelect.selectPhoto(params = PictureSelectParams(1)).firstOrNull()
                            ?.getOrNull()?.firstOrNull()
                }
            }) {
                Text("选择图库", modifier = Modifier.padding(24.dp).fillMaxWidth())
            }
            Text("path:${pictureMedia?.path}")
            AsyncImage(
                model = pictureMedia?.preview?.toByteArray(),
                contentDescription = "Image",
                modifier = Modifier.fillMaxWidth().wrapContentHeight()
            )
        }
    }
}