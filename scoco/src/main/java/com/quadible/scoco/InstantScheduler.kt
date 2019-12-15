package com.quadible.scoco

import kotlinx.coroutines.CancellableContinuation
import kotlin.coroutines.resume

/**
 * A [ContinuationScheduler] that is responsible to resume a continuation instantly, ignoring any
 * delay.
 */
class InstantScheduler : ContinuationScheduler {

    override fun schedule(delay: Long, continuation: CancellableContinuation<Unit>) {
        continuation.resume(Unit)
    }

    override fun resumeContinuations(isDelayPassed: (Long) -> Boolean) {
        /* no pending scheduled continuation exists */
    }

}
