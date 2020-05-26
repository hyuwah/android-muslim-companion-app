package io.github.hyuwah.muslimcompanionapp.presentation.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

class ActionStateLiveData<T>(
        private val coroutineContext: CoroutineContext,
        fetchData: (suspend () -> Response<T>)
) {
    private val action = MutableLiveData<Action>()
    private var data: T? = null // backing data

    val state = action.switchMap {
        liveData(context = coroutineContext) {
            when (action.value) {
                Action.Load -> {
                    emit(UIState.Loading)
                }

                Action.SwipeRefresh -> {
                    emit(UIState.SwipeRefreshing)
                }

                Action.Retry -> {
                    emit(UIState.Retrying)
                }
            }

            try {
                val response = fetchData()
                val body = response.body()
                when {
                    response.isSuccessful && body != null -> {
                        data = body
                        emit(UIState.Success(body))
                    }
                    action.value == Action.SwipeRefresh -> {
                        emit(UIState.SwipeRefreshFailure(Exception()))
                    }
                    else -> {
                        emit(UIState.Failure(Exception()))
                    }
                }
            } catch (exception: Exception) {
                when {
                    action.value == Action.SwipeRefresh -> {
                        emit(UIState.SwipeRefreshFailure(Exception()))
                        data?.let {
                            // emit success with existing data
                            emit(UIState.Success<T>(it))
                        }
                    }
                    else -> {
                        emit(UIState.Failure(Exception()))
                    }
                }
            }
        }
    }

    // Helpers for triggering different actions

    fun retry() {
        action.value = Action.Retry
    }

    fun swipeRefresh() {
        action.value = Action.SwipeRefresh
    }

    fun load() {
        action.value = Action.Load
    }
}