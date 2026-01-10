package compose.project.listingstowordconverter

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import compose.project.listingstowordconverter.di.jvmModule
import compose.project.listingstowordconverter.di.viewModelModule
import org.koin.core.context.startKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ListingsToWordConverter",
    ) {
        startKoin {
            modules(viewModelModule+ jvmModule)
        }
        DesktopApp()
    }
}