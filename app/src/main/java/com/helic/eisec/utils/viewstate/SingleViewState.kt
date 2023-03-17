package com.helic.eisec.utils.viewstate

import com.helic.eisec.model.task.Task

sealed class SingleViewState {
    // Represents different states for the Single Task Details Screen
    object Empty : SingleViewState()
    object Loading : SingleViewState()
    data class Success(val task: Task) : SingleViewState()
    data class Error(val exception: Throwable) : SingleViewState()
}