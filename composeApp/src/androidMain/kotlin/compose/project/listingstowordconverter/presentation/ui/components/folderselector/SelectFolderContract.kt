package compose.project.listingstowordconverter.presentation.ui.components.folderselector

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContract

class SelectFolderContract: ActivityResultContract<Unit, String?>() {
    override fun createIntent(
        context: Context,
        input: Unit
    ): Intent {
        return Intent(Intent.ACTION_OPEN_DOCUMENT_TREE).apply {
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION)
        }
    }

    override fun parseResult(
        resultCode: Int,
        intent: Intent?
    ): String? {
        if (resultCode != Activity.RESULT_OK || intent == null) return null
        return intent.data?.path
    }

}