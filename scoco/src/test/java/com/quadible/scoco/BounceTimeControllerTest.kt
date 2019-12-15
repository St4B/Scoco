package com.quadible.scoco
import io.mockk.*
import org.amshove.kluent.`should equal`
import org.junit.Test

class BounceTimeControllerTest {

    private val scheduler: ContinuationScheduler = mockk(relaxed = true)

    private val controller = BounceTimeController(scheduler)

    @Test
    fun `should always be moved to a specific time instance`() {
        //Given
        val lastTimeInstance = 90L

        //When
        controller.bounceTo(121L)
        controller.bounceTo(345L)
        controller.bounceTo(lastTimeInstance)

        //Then
        controller.time `should equal` lastTimeInstance
    }

    @Test
    fun `should trigger resume of continuations`() {
        //Given
        val time = 123L
        val slot = slot<(Long) -> Boolean>()

        //When
        controller.bounceTo(time)

        //Then
        verify(exactly = 1) { scheduler.resumeContinuations(capture(slot)) }
        slot.captured `should equal` (controller as TimeController)::isDelayPassed
    }

}