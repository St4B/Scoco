package com.quadible.scoco

/**
 * A [TimeController] that helps us to bounce between different time instances. 
 */
class BounceTimeController(scheduler: ContinuationScheduler) : TimeController(scheduler) {

    override fun isDelayPassed(delay: Long) = time >= delay

    /**
     * Set [TimeController] to a specific time instance.
     */
    fun bounceTo(millis: Long) {
        time = millis
    }

}