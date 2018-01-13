package io.bkraszewski.reactivetictactoe.game

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.GameState
import io.bkraszewski.reactivetictactoe.model.Player
import io.bkraszewski.reactivetictactoe.service.GameService
import io.bkraszewski.reactivetictactoe.service.PlayerService
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers


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
    fun shoudJoinGame(){
        val game = Game("123","Test", null, 0, IntRange(0, 8).associateBy({ it }, { "" }))
        whenever(gameService.joinGame(any())).thenReturn(Single.never())

        cut.onJoinGame(game)

        verify(gameService).joinGame(game)
        verify(view).setState(GameState.CONNECTING)
    }

    @Test
    fun shouldSubscribeForTurnChangeOnJoin(){
        val game = Game("123","Test", null, 0, IntRange(0, 8).associateBy({ it }, { "" }))
        whenever(gameService.joinGame(any())).thenReturn(Single.just(game))
        whenever(gameService.subscribeToGameUpdate(any())).thenReturn(Observable.empty())

        cut.onJoinGame(game)

        verify(gameService).subscribeToGameUpdate(game.id)
    }

    private fun setupMockedGameCreation(): Game {
        val game = Game("123","Test", null, 0, IntRange(0, 8).associateBy({ it }, { "" }))
        whenever(gameService.createGame(any())).thenReturn(Single.just(game))
        return game
    }

    private fun emptyBoard(): List<String> {
        return IntRange(0, 8).map { "" }
    }

}
