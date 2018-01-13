package io.bkraszewski.reactivetictactoe.game

import android.text.TextUtils
import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.GameState
import io.bkraszewski.reactivetictactoe.service.GameService
import io.bkraszewski.reactivetictactoe.service.PlayerService
import io.reactivex.disposables.CompositeDisposable

class GamePresenter(private val view: GameContract.View, private val gameService: GameService, private val playerService: PlayerService) : GameContract.Presenter {


    private val map = IntRange(0, 8).map { "" }
    private val disposables = CompositeDisposable()
    val player = playerService.provide()

    override fun onCreateGame() {
        view.enableBoard(false)
        view.setState(GameState.WAITING_FOR_OPONENT)
        view.renderBoard(map)


        gameService.createGame(player)
                .subscribe({ game: Game, t2: Throwable? ->

                    if (t2 != null) {
                        //handle
                        t2.printStackTrace()
                        return@subscribe
                    }

                    listenForTurnChange(game.id)
                })
    }

    override fun onJoinGame(game: Game) {
        view.setState(GameState.CONNECTING)
        disposables.add(gameService.joinGame(game).subscribe({ game, t2 ->
            if (t2 != null) {
                //handle
                t2.printStackTrace()
                return@subscribe
            }

            listenForTurnChange(game.id)
        }))
    }

    private fun listenForTurnChange(gameId: String) {

        disposables.add(gameService.subscribeToGameUpdate(gameId)
                .subscribe({game: Game ->
                    onGameUpdate(game)
                }))
    }

    fun onGameUpdate(game: Game) {
        val players = listOf(game.hostName, game.playerName!!)
        val moves = game.board.values.filter { !it.isEmpty() }.count()
        val nextToMoveIndex = (game.startPlayerIndex + moves) % 2

        val nextPlayer = players[nextToMoveIndex]

        val current = nextPlayer == player.name
        view.setState(GameState.GAME_RUNNING)
        view.enableBoard(current)
        view.setTurn(nextPlayer)
    }

    override fun onViewReady() {
    }

    override fun detachView() {
        disposables.clear()
    }

}
