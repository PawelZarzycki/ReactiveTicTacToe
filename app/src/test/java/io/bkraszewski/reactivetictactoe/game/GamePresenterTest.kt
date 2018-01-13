package io.bkraszewski.reactivetictactoe.game

import com.nhaarman.mockito_kotlin.*
import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.GameState
import io.bkraszewski.reactivetictactoe.model.Player
import io.bkraszewski.reactivetictactoe.model.Symbol
import io.bkraszewski.reactivetictactoe.service.GameService
import io.bkraszewski.reactivetictactoe.service.PlayerService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test


class GamePresenterTest {

    lateinit var cut: GamePresenter

    private lateinit var gameService: GameService

    private lateinit var playerService: PlayerService

    private lateinit var view: GameContract.View

    @Before
    fun setup() {
        gameService = mock()
        playerService = mock()
        view = mock()

        val player = Player("Tester")
        whenever(playerService.provide()).thenReturn(player)

        cut = GamePresenter(view, gameService, playerService)
    }

    @Test
    fun shouldRenderUiForCreatingGameState() {
        whenever(gameService.createGame(any())).thenReturn(Single.never())
        cut.onCreateGame()

        var emptyBoard = emptyBoard()
        verify(view).renderBoard(emptyBoard)
        verify(view).enableBoard(false)
        verify(view).setState(GameState.WAITING_FOR_OPONENT)
    }

    @Test
    fun shouldSubscribeForTurnChangeOnCreateGameSuccess() {
        val game = setupMockedGameCreation()
        whenever(gameService.subscribeToGameUpdate(any())).thenReturn(Observable.empty())
        cut.onCreateGame()

        verify(gameService).subscribeToGameUpdate(game.id)
    }

    @Test
    fun shoudJoinGame() {
        val game = Game("123", "Test", null, 0, IntRange(0, 8).associateBy({ it }, { "" }).toMutableMap())
        whenever(gameService.joinGame(any())).thenReturn(Single.never())

        cut.onJoinGame(game)

        verify(gameService).joinGame(game)
        verify(view).setState(GameState.CONNECTING)
    }

    @Test
    fun shouldSubscribeForTurnChangeOnJoin() {
        val game = Game("123", "Test", null, 0, IntRange(0, 8).associateBy({ it }, { "" }).toMutableMap())
        whenever(gameService.joinGame(any())).thenReturn(Single.just(game))
        whenever(gameService.subscribeToGameUpdate(any())).thenReturn(Observable.empty())

        cut.onJoinGame(game)

        verify(gameService).subscribeToGameUpdate(game.id)
    }

    @Test
    fun shouldEnableBoardOnUserTurn() {
        val subject = PublishSubject.create<Game>()
        val game = Game("123", "Tester", "Other Player", 0, emptyBoardAsMap())
        whenever(gameService.subscribeToGameUpdate(any())).thenReturn(subject)
        whenever(gameService.createGame(any())).thenReturn(Single.just(game))

        whenever(playerService.provide()).thenReturn(Player("test"))

        cut.onCreateGame()
        subject.onNext(game)

        verify(view, atLeastOnce()).setState(GameState.GAME_RUNNING)
        verify(view).setTurn("Tester")
        verify(view, atLeastOnce()).enableBoard(true)
    }

    @Test
    fun shouldRenderUiForOpponentTurn(){
        val game = Game("123", "Tester", "Other Player", 1, emptyBoardAsMap())

        cut.onGameUpdate(game)

        verify(view).setTurn("Other Player")
        verify(view, atLeastOnce()).enableBoard(false)
    }

    @Test
    fun shouldRenderUiForMyTurnAfterFewMoves(){
        val map= emptyBoardAsMap().toMutableMap()
        map[0] = Symbol.Os
        map[3] = Symbol.CROSS
        map[6] = Symbol.Os
        map[7] = Symbol.CROSS

        val game = Game("123", "Tester", "Other Player", 0, emptyBoardAsMap())

        cut.onGameUpdate(game)

        verify(view, atLeastOnce()).setState(GameState.GAME_RUNNING)
        verify(view).setTurn("Tester")
        verify(view, atLeastOnce()).enableBoard(true)
    }

    @Test
    fun shouldRenderUiForSelectingFirstUserMove(){
        val game = Game("123", "Tester", "Other Player", 0, emptyBoardAsMap())
        cut.onGameUpdate(game)

        cut.onMove(0)

        verify(view).enableBoard(false)

        val board = IntRange(0, 8).map { "" }.toMutableList()
        board[0] = Symbol.CROSS

        verify(view).renderBoard(board)
        verify(view).enableBoard(false)
        verify(view).setTurn("Other Player")
    }

    private fun setupMockedGameCreation(): Game {
        val game = Game("123", "Test", null, 0, emptyBoardAsMap())
        whenever(gameService.createGame(any())).thenReturn(Single.just(game))
        return game
    }

    private fun emptyBoard(): List<String> {
        return IntRange(0, 8).map { "" }
    }

    private fun emptyBoardAsMap(): MutableMap<Int, String> {
        return IntRange(0,8).associateBy({it}, {""}).toMutableMap()
    }

}
