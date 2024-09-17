package org.robphi.groupnotes.auth

import kotlinx.coroutines.flow.StateFlow
import org.robphi.groupnotes.api.UserId

interface Auth {

    val userId: StateFlow<UserId?>
    /** Get userId, blocking until it's available. */
    fun getUserIdBlocking(): UserId
    suspend fun getUserId(): UserId

}