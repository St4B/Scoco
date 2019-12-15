package com.quadible.scoco

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * A [CoroutineScope] that is driven by a scheduler.
 */
class Scope(private val scheduler: ContinuationScheduler = InstantScheduler()) :
    CoroutineScope {

    private val job = Job()

    override val coroutineContext: CoroutineContext
        get() = job + Context(scheduler)

}

/**
 * https://stackoverflow.com/questions/47171302/unit-testing-a-kotlin-coroutine-with-delay
 */
@UseExperimental(InternalCoroutinesApi::class)
private class Context(private val scheduler: ContinuationScheduler) : CoroutineDispatcher(),
    Delay {

    @UseExperimental(InternalCoroutinesApi::class)
    override fun scheduleResumeAfterDelay(
        timeMillis: Long,
        continuation: CancellableContinuation<Unit>
    ) {
        scheduler.schedule(timeMillis, continuation)
    }

    override fun dispatch(context: CoroutineContext, block: Runnable) {
        block.run()
    }

}
