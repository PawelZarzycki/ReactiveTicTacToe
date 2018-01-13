package io.bkraszewski.reactivetictactoe.service

import io.bkraszewski.reactivetictactoe.model.Player

class PlayerServiceImpl : PlayerService{
    override fun provide(): Player {
        return Player("Tester")
    }


}
