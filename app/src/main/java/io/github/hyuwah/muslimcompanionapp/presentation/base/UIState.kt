package io.github.hyuwah.muslimcompanionapp.presentation.base


sealed class UIState<out R> {
    object Loading : UIState<Nothing>()
    object Retrying : UIState<Nothing>()
    object SwipeRefreshing : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Failure(val exception: Exception) : UIState<Nothing>()
    data class SwipeRefreshFailure(val exception: Exception) : UIState<Nothing>()
}