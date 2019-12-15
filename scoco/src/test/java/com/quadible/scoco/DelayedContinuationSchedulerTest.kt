package com.quadible.scoco

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CancellableContinuation
import org.junit.Test
import kotlin.coroutines.resume

class DelayedContinuationSchedulerTest {

    @Test
    fun `should not run continuation if delay has not passed`() {
        //Given
        val scheduler = DelayedContinuationScheduler()
        val continuation: CancellableContinuation<Unit> = mockk(relaxed = true)
        val isDelayPassed: (Long) -> Boolean = { _ -> false }

        //When
        scheduler.schedule(1232L, continuation)
        scheduler.resumeContinuations(isDelayPassed)

        //Then
        verify(exactly = 0) { continuation.resume(Unit) }
    }

    @Test
    fun `should run continuation if delay has passed`() {
        //Given
        val scheduler = DelayedContinuationScheduler()
        val continuation: CancellableContinuation<Unit> = mockk(relaxed = true)
        val isDelayPassed: (Long) -> Boolean = { _ -> true }

        //When
        scheduler.schedule(1232L, continuation)
        scheduler.resumeContinuations(isDelayPassed)

        //Then
        verify(exactly = 1) { continuation.resume(Unit) }
    }

}