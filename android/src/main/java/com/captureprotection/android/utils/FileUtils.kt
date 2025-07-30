package com.captureprotection.utils

import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.facebook.react.bridge.*
import java.util.Locale

class FileUtils {
    companion object {
        fun isImageUri(uri: Uri?): Boolean {
            return uri?.toString()
                    ?.matches(Regex("${MediaStore.Images.Media.EXTERNAL_CONTENT_URI}/[0-9]+"))
                    ?: false
        }

        fun isScreenshotFile(reactContext: ReactApplicationContext, uri: Uri): Boolean {
            var cursor: Cursor? = null
            try {
                cursor =
                        reactContext.contentResolver.query(
                                uri,
                                arrayOf(MediaStore.Images.Media.DATA),
                                null,
                                null,
                                null
                        )
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                    val path = if(columnIndex >= 0) cursor.getString(columnIndex) else null
                    return (path != null && path.lowercase(Locale.ROOT).contains("screenshots"))
                }
                return false
            } finally {
                cursor?.close()
            }
        }
    }
}
