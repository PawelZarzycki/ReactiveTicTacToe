package io.bkraszewski.reactivetictactoe.service

import android.os.Build
import io.bkraszewski.reactivetictactoe.model.Player

class PlayerServiceImpl : PlayerService{
    override fun provide(): Player {
        return Player(Build.DEVICE)
    }

}
