package com.example.multimodulecomposecryptoapp.core.network

import com.example.multimodulecomposecryptoapp.core.common.AuthorizationException
import com.example.multimodulecomposecryptoapp.core.common.BadRequestException
import com.example.multimodulecomposecryptoapp.core.common.NetworkException
import com.example.multimodulecomposecryptoapp.core.common.NotFoundException
import com.example.multimodulecomposecryptoapp.core.common.Resource
import com.example.multimodulecomposecryptoapp.core.common.UnknownException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

suspend fun <T : Any> safeApiCall(apiToBeCalled: suspend () -> T): Resource<T> {
    return withContext(Dispatchers.IO) {
        try {
            Resource.Success(apiToBeCalled())
        } catch (e: HttpException) {
            val errorMessage = e.message() ?: "An unknown error occurred, please try again later."
            when (e.code()) {
                400 -> Resource.Error(BadRequestException(errorMessage))
                401 -> Resource.Error(AuthorizationException(errorMessage))
                404 -> Resource.Error(NotFoundException(errorMessage))
                else -> Resource.Error(UnknownException(errorMessage))
            }
        } catch (e: IOException) {
            Resource.Error(NetworkException())
        } catch (e: Exception) {
            Resource.Error(UnknownException(e.message ?: "An unknown error occurred"))
        }
    }
} 