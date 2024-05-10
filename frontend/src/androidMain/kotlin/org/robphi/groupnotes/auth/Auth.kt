package org.robphi.groupnotes.auth

import kotlinx.coroutines.flow.StateFlow

interface Auth {

    val userId: StateFlow<Long?>

}