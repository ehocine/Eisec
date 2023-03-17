package com.helic.eisec.utils

import com.helic.eisec.model.task.Priority


fun calculatePriority(priorityAverage: Int): Priority {

    return when {
        priorityAverage >= 4 -> {
            Priority.URGENT
        }
        priorityAverage >= 3 -> {
            Priority.IMPORTANT
        }
        priorityAverage >= 2 -> {
            Priority.DELEGATE
        }
        priorityAverage == 1 -> {
            Priority.DUMP
        }
        else -> {
            Priority.DUMP
        }
    }
}

/**
 *@return Pair(urgency, importance)
 */
fun getUrgencyImportanceFromPriority(priority: String): Pair<Int, Int> {
    return when (Priority.valueOf(priority)) {
        Priority.URGENT -> Pair(5, 5) // (5/2) + 5 = 7.5
        Priority.IMPORTANT -> Pair(3, 2) // (2/2) + 3 = 3
        Priority.DELEGATE -> Pair(2, 1) // (1/2) + 2 = 2.5
        Priority.DUMP -> Pair(1, 1) // (1/2) + 1 = 1.5
    }
}