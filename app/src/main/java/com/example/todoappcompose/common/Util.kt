package com.shayan.composeBatmanMovie.common

import com.shayan.composeBatmanMovie.vo.Resource

object Util {

    suspend fun <T>  wrapTryCatch(body: suspend () -> T): Resource<T> {

        return try {
            Resource.Success(body())
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage?:"error")
        }

    }
}