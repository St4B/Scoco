package com.quadible.scoco

import kotlin.properties.Delegates

/**
 * Base class for controlling time in a Coroutine.
 */
abstract class TimeController(private val scheduler: ContinuationScheduler) {

    internal var time by Delegates.observable(0L, { _, _, _ ->
        scheduler.resumeContinuations(::isDelayPassed)
    })

    /**
     * Used by [ContinuationScheduler] in order to define if the delay has passed in order to
     * resume a continuation.
     */
    abstract fun isDelayPassed(delay: Long): Boolean

}