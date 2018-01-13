package io.bkraszewski.reactivetictactoe.service

object DI{
    val playerService: PlayerService by lazy { PlayerServiceImpl() }
    val gameService: GameService by lazy { GameServiceImpl() }
}
