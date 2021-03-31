package com.suy.mybookshelf.utils

data class Resource<out T>(val status: Status, val data: T?, val message: Int?) {
    companion object {
        fun <T> loading(): Resource<T> = Resource(Status.LOADING, null, null)
        fun <T> success(data: T?): Resource<T> = Resource(Status.SUCCESS, data, null)
        fun <T> error(msg: Int): Resource<T> = Resource(Status.ERROR, null, msg)
    }
}