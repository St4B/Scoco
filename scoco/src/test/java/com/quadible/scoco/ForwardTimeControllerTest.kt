package com.quadible.scoco

import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.amshove.kluent.`should equal`
import org.junit.Test

class ForwardTimeControllerTest {

    private val scheduler: ContinuationScheduler = mockk(relaxed = true)

    private val controller = ForwardTimeController(scheduler)

    @Test
    fun `should always `() {
        //Given
        val firstInterval = 109L
        val secondInterval = 340L

        //When
        controller.forwardBy(firstInterval)
        controller.forwardBy(secondInterval)

        //Then
        controller.time `should equal` firstInterval + secondInterval
    }

    @Test(expected = MoveBackwardException::class)
    fun `should throw exception if an interval is negative`() {
        //Given
        val interval = -121L

        //When
        controller.forwardBy(interval)

        //Then result is handled with annotation :)
    }

    @Test
    fun `should trigger resume of continuations`() {
        //Given
        val time = 123L
        val slot = slot<(Long) -> Boolean>()

        //When
        controller.forwardBy(time)

        //Then
        verify(exactly = 1) { scheduler.resumeContinuations(capture(slot)) }
        slot.captured `should equal` (controller as TimeController)::isDelayPassed
    }

}