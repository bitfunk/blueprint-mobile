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

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.platform.app.InstrumentationRegistry
import java.io.FileNotFoundException
import java.io.InputStream

fun loadByteArrayAsset(path: String): ByteArray = loadAsset(path) { inputStream ->
    inputStream.readBytes()
}

fun loadBitmapAsset(path: String): Bitmap? = loadAsset(path) { inputStream ->
    inputStream.use { BitmapFactory.decodeStream(it) }
}

fun <T> loadAsset(path: String, mapping: (InputStream) -> T): T {
    val assets = InstrumentationRegistry.getInstrumentation().context.assets
    return try {
        val file = assets.open(path)
        mapping(file)
    } catch (exception: Throwable) {
        throw FileNotFoundException("File not found: $path")
    }
}
