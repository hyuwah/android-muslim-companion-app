package io.github.hyuwah.muslimcompanionapp.presentation.base

sealed class Action {
    object Load : Action()
    object SwipeRefresh : Action()
    object Retry : Action()
}