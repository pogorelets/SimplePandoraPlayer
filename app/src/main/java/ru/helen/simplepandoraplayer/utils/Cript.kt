package ru.helen.simplepandoraplayer.utils

import ru.helen.simplepandoraplayer.repository.Storage

import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class Cript(val encryptKey: String = Storage.encryptKey,
            val decryptKey: String = Storage.decryptKey,
            var algorithm: String = "Blowfish",
            var strategy: String = "ECB",
            var padding: String = "PKCS5Padding") {

    val transform get() = "$algorithm/$strategy/$padding"

    fun encrypt(decrypted: String, key: String = encryptKey): ByteArray {
        return cripting(decrypted, key, Cipher.ENCRYPT_MODE)
    }

    fun decrypt(encrypted: ByteArray, key: String = decryptKey): ByteArray {
        return cripting(encrypted, key, Cipher.DECRYPT_MODE)
    }

    private fun getKeySpec(key: String): SecretKeySpec {
        val keyData = key.toByteArray(Charsets.UTF_8)
        return SecretKeySpec(keyData, algorithm)
    }

    private fun cripting(untouched: String, key: String, mode: Int): ByteArray {
        return cripting(untouched.toByteArray(Charsets.UTF_8), key, mode)
    }

    private fun cripting(untouched: ByteArray, key: String, mode: Int): ByteArray {
        val cipher = Cipher.getInstance(transform)
        cipher.init(mode, getKeySpec(key))
        return cipher.doFinal(untouched)
    }




}