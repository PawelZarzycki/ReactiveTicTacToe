package io.bkraszewski.reactivetictactoe.game

import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.GameState
import io.bkraszewski.reactivetictactoe.model.Symbol
import io.bkraszewski.reactivetictactoe.service.GameService
import io.bkraszewski.reactivetictactoe.service.PlayerService
import io.reactivex.disposables.CompositeDisposable

class GamePresenter(private val view: GameContract.View, private val gameService: GameService, private val playerService: PlayerService) : GameContract.Presenter {

    private val map = IntRange(0, 8).map { "" }
    private val disposables = CompositeDisposable()
    val player = playerService.provide()
    private lateinit var playerSymbol: String
    private lateinit var game: Game

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

                    this.game = game
                    listenForTurnChange(game.id)
                })
    }

    override fun onJoinGame(game: Game) {
        this.game = game
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
                .subscribe({ game: Game ->
                    onGameUpdate(game)
                }))
    }

    fun onGameUpdate(game: Game) {
        this.game = game
        val players = listOf(game.hostName, game.playerName!!)
        val moves = game.board.values.filter { !it.isEmpty() }.count()
        val nextToMoveIndex = (game.startPlayerIndex + moves) % 2

        val nextPlayer = players[nextToMoveIndex]

        val current = nextPlayer == player.name

        val wasFirst = players.indexOf(player.name) == game.startPlayerIndex
        playerSymbol = if (wasFirst) Symbol.CROSS else Symbol.Os

        view.setState(GameState.GAME_RUNNING)
        view.enableBoard(current)
        view.setTurn(nextPlayer)
    }

    override fun onViewReady() {
    }

    override fun detachView() {
        disposables.clear()
    }

    override fun onMove(fieldIndex: Int) {
        game.board[fieldIndex] = playerSymbol
        view.renderBoard(toList(game.board))
        onGameUpdate(game)
        gameService.updateGame(game)

    }

    private fun toList(board: MutableMap<Int, String>): List<String> {
        //todo find clever way
        val list = MutableList(9, { i -> "" })
        for ((key, value) in board.entries) {
            list[key] = value
        }

        return list
    }

}
