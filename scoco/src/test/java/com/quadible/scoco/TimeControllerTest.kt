package com.quadible.scoco

import io.mockk.*
import org.amshove.kluent.`should equal`
import org.junit.Test

class TimeControllerTest {

    @Test
    fun `should trigger resume of continuations`() {
        //Given
        val scheduler: ContinuationScheduler = mockk()
        val controller = TestInstanceTimeController(scheduler)
        val slot = slot<(Long) -> Boolean>()
        every { scheduler.resumeContinuations(capture(slot)) } just Runs

        //When
        controller.time = 124

        //Then
        verify(exactly = 1) { scheduler.resumeContinuations(any()) }
        slot.captured `should equal` (controller as TimeController)::isDelayPassed
    }

}

private class TestInstanceTimeController(scheduler: ContinuationScheduler): TimeController(scheduler) {

    override fun isDelayPassed(delay: Long) = true

}
