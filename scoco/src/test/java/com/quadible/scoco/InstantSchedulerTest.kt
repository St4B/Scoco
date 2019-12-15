package com.quadible.scoco

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.CancellableContinuation
import org.junit.Test
import kotlin.coroutines.resume

class InstantSchedulerTest {

    @Test
    fun `should resume immediately`() {
        //Given
        val scheduler = InstantScheduler()
        val continuation: CancellableContinuation<Unit> = mockk(relaxed = true)

        //When
        scheduler.schedule(1232L, continuation)

        //Then
        verify(exactly = 1) { continuation.resume(Unit) }
    }

}