package ru.helen.simplepandoraplayer.api

data class Responce<T> (val stat: String,
                        val result: T,
                        val message: String?, val code: Int)


