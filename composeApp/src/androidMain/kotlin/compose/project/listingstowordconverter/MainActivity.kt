package compose.project.listingstowordconverter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import compose.project.listingstowordconverter.di.androidModule
import compose.project.listingstowordconverter.di.appFileSystemModule
import compose.project.listingstowordconverter.di.viewModelModule
import compose.project.listingstowordconverter.presentation.ui.components.folderselector.FolderSelector
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        startKoin {
            androidContext(this@MainActivity)
            modules(appFileSystemModule + viewModelModule + androidModule)
        }
        getKoin().get<FolderSelector>().init(this)
        setContent {
            AndroidApp()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    AndroidApp()
}