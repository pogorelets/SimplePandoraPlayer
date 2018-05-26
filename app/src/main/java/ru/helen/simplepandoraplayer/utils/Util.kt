package ru.helen.simplepandoraplayer.utils

fun String.hexStringToByteArray(): ByteArray {
    val len = length
    val data = ByteArray(len / 2)
    var i = 0
    while (i < len) {
        data[i / 2] = ((Character.digit(this[i], 16) shl 4) + Character.digit(this[i + 1], 16)).toByte()
        i += 2
    }
    return data
}

private val hexArray = "0123456789abcdef".toCharArray()
fun ByteArray.bytesToHex(): String {
    val hexChars = CharArray(size * 2)
    for (j in indices) {
        val v = this[j].toInt() and 0xFF
        hexChars[j * 2] = hexArray[v.ushr(4)]
        hexChars[j * 2 + 1] = hexArray[v and 0x0F]
    }
    return String(hexChars)
}