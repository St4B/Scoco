package com.quadible.scoco

import kotlinx.coroutines.CancellableContinuation

data class ScheduledContinuation(
    val delay: Long,
    val continuation: CancellableContinuation<Unit>
)