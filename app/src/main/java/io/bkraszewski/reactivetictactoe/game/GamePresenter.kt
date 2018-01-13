package io.bkraszewski.reactivetictactoe.game

import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.GameState
import io.bkraszewski.reactivetictactoe.service.GameService
import io.bkraszewski.reactivetictactoe.service.PlayerService
import io.reactivex.disposables.CompositeDisposable

class GamePresenter(private val view: GameContract.View, private val gameService: GameService, private val playerService: PlayerService) : GameContract.Presenter {


    private val map = IntRange(0, 8).map { "" }
    private val disposables = CompositeDisposable()

    override fun onCreateGame() {
        view.enableBoard(false)
        view.setState(GameState.WAITING_FOR_OPONENT)
        view.renderBoard(map)

        val player = playerService.provide()
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
                .subscribe({

                }))
    }

    override fun onViewReady() {
    }

    override fun detachView() {
        disposables.clear()
    }

}
