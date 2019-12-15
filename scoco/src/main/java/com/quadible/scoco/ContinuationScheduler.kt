package com.quadible.scoco

import kotlinx.coroutines.CancellableContinuation

/**
 * Base component that is used in order to schedule execution of continuations.
 */
interface ContinuationScheduler {

    /**
     * Schedules when a continuation is going to be continued.
     */
    fun schedule(delay: Long, continuation: CancellableContinuation<Unit>)

    /**
     * It is triggered by [TimeController] when the [TimeController.time] has changed in order to
     * resume continuation. The continuations that are going to be resumed, should be the ones that
     * satisfy [isDelayPassed].
     * @param isDelayPassed A function that help us to realize if a delay has passed. Basically, this
     * corresponds to [TimeController.isDelayPassed] that is bound to this scheduler.
     */
    fun resumeContinuations(isDelayPassed: (Long) -> Boolean)

}