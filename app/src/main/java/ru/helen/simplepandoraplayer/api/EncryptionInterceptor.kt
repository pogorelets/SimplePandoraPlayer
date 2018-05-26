package ru.helen.simplepandoraplayer.api

import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.Response
import okio.Buffer
import ru.helen.simplepandoraplayer.utils.Cript
import ru.helen.simplepandoraplayer.utils.bytesToHex

class EncryptionInterceptor: Interceptor {

    companion object {
        const val ENC_HEADER_TAG = "ENCRYPT_ME_PLZ"
    }

    private val TAG = EncryptionInterceptor::class.java.simpleName

    override fun intercept(chain: Interceptor.Chain?): Response {
        var request = chain?.request()
        if (request != null && request.header(ENC_HEADER_TAG) == "true") {
            // Get body as string
            val oldBody = request.body()
            val buffer = Buffer()
            oldBody?.writeTo(buffer)
            val oldBodyString = buffer.readUtf8()

            // Perform encryption
            val cript = Cript()
            val encryptedByteArray = cript.encrypt(oldBodyString)
            val encodedString = encryptedByteArray.bytesToHex()

            // Rebuild request
            val mediaType = MediaType.parse("text/plain; charset=utf-8")
            val newRequestBody = RequestBody.create(mediaType, encodedString)

            Log.e("NEWBODY", newRequestBody.toString())
            request = request.newBuilder()
                    .removeHeader(ENC_HEADER_TAG)
                    .header("Content-Type", newRequestBody.contentType().toString())
                    .header("Content-Length", newRequestBody.contentLength().toString())
                    .method(request.method(), newRequestBody)
                    .build()
        }
        return chain!!.proceed(request)
    }

}