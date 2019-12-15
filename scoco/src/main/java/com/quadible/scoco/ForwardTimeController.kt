package com.quadible.scoco

/**
 * A [TimeController] that advances current time forwards, by a specified amount of millis.
 */
class ForwardTimeController(scheduler: ContinuationScheduler) : TimeController(scheduler) {

    override fun isDelayPassed(delay: Long) = time >= delay

    /**
     * Advances current time forwards, by a specified amount of millis.
     * @param millis The amount of time to move forward.
     * @exception MoveBackwardException if [millis] are negative
     */
    fun forwardBy(millis: Long) {
        if (millis < 0) {
            throw MoveBackwardException()
        }

        time += millis
    }

}

class MoveBackwardException: Exception("Backward operation is not supported.")