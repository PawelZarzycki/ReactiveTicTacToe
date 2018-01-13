package io.bkraszewski.reactivetictactoe.service

import io.bkraszewski.reactivetictactoe.model.Player

interface PlayerService {
    fun provide(): Player
}
