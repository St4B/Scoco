package com.quadible.scoco

import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume

/**
 * A [ContinuationScheduler] that is responsible to resume a continuation when the given delay has
 * passed.
 */
class DelayedContinuationScheduler : ContinuationScheduler {

    private val scheduled = mutableListOf<ScheduledContinuation>()

    override fun schedule(delay: Long, continuation: CancellableContinuation<Unit>) {
        scheduled.add(ScheduledContinuation(delay, continuation))
    }

    override fun resumeContinuations(isDelayPassed: (Long) -> Boolean) {
        scheduled.filter { isDelayPassed(it.delay) }
            .forEach {
                scheduled.remove(it)
                it.continuation.resume(Unit)
            }
    }

}