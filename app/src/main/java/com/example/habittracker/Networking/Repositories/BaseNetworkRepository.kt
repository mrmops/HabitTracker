package com.example.habittracker.Networking.Repositories

import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.coroutines.CoroutineContext

abstract class BaseNetworkRepository : CoroutineScope {
    private var job = Job()
    override val coroutineContext: CoroutineContext
        get() = IO + job + CoroutineExceptionHandler { _, e -> throw e }

    protected abstract val logTag: String

    protected suspend fun <T> doWhileNotSuccessWithResult(
        delayTimeMillis: Long,
        action: suspend () -> T
    ): T =
        doInIOCoroutineWithResult {
            while (true) {
                try {
                    return@doInIOCoroutineWithResult action.invoke()
                } catch (e: UnknownHostException) {
                    e.printStackTrace()
                    Log.d(logTag, "Server is unreachable")
                } catch (e: SocketTimeoutException) {
                    e.printStackTrace()
                    Log.d(logTag, "No internet connection")
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e(logTag, "Critical internet error!")
                }
                delay(delayTimeMillis)
            }
            throw NotImplementedError()
        }

    protected suspend fun <T> tryMakeAction(
        action: suspend () -> T,
        onSuccess: (suspend (T) -> Unit)? = null,
        onError: (suspend (e: Exception) -> Unit)? = null
    ) = doInIOCoroutineWithResult {
        try {
            onSuccess?.invoke(action.invoke())
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(logTag, e.message ?: "Critical internet error!")
            onError?.invoke(e)
        }
    }

    protected suspend fun <T> doInIOCoroutineWithResult(action: suspend () -> T): T =
        withContext(IO) { action.invoke() }

}