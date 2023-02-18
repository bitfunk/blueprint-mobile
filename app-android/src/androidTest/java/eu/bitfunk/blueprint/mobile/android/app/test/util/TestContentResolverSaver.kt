/*
 * ISC License
 *
 * Copyright (c) 2022. Wolf-Martell Montwé (bitfunk)
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH
 * REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF MERCHANTABILITY
 * AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
 * INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM
 * LOSS OF USE, DATA OR PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR
 * OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
 * PERFORMANCE OF THIS SOFTWARE.
 */

package eu.bitfunk.blueprint.mobile.android.app.test.util

import android.content.ContentValues
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.test.platform.app.InstrumentationRegistry
import java.io.OutputStream

fun saveBitmapInDownloads(name: String, bitmap: Bitmap) {
    val contentValues = ContentValues().apply {
        put(MediaStore.MediaColumns.DISPLAY_NAME, "screenshot/$name")
        put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
        put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
    }

    saveWithContentResolver(contentValues, MediaStore.Downloads.EXTERNAL_CONTENT_URI) { outputStream ->
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
    }
}

fun saveWithContentResolver(
    contentValues: ContentValues,
    contentUri: Uri,
    mapping: (OutputStream) -> Unit,
) {
    val resolver = InstrumentationRegistry.getInstrumentation().targetContext.contentResolver
    val uri: Uri? = resolver.insert(contentUri, contentValues)

    if (uri != null) {
        runCatching {
            resolver.openOutputStream(uri)?.use { output ->
                mapping(output)
            }
        }.getOrElse {
            uri.let {
                resolver.delete(uri, null, null)
            }
        }
    } else {
        throw IllegalArgumentException("ContentResolver failed to insert: $contentUri")
    }
}
