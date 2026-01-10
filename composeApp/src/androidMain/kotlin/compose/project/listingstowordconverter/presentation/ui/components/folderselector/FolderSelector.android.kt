package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import android.content.Context
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import java.lang.ref.WeakReference
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class FolderSelector actual constructor() {
    private var _context: WeakReference<Context?> = WeakReference(null)
    private var launcher: ActivityResultLauncher<Unit>? = null
    private var callback: ((String?) -> Unit)? = null

    fun init(context: Context) {
        _context = WeakReference(context)
        val activity = context as ComponentActivity
        launcher = activity.registerForActivityResult(
            SelectFolderContract()
        ) { result ->
            callback?.invoke(result)
            callback = null
        }
    }

    actual suspend fun selectFolder(): String? {
        return try {
            suspendCoroutine { cont ->
                callback = { result -> cont.resume(result) }
                launcher?.launch(Unit)
            }
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }
}