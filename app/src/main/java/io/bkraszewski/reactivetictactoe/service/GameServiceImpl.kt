package io.bkraszewski.reactivetictactoe.service

import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.Player
import io.reactivex.Observable
import io.reactivex.Single

class GameServiceImpl : GameService {

    override fun joinGame(game: Game): Single<Game> {
        val game = Game("123", "Test", null, 0, IntRange(0, 8).associateBy({ it }, { "" }).toMutableMap())
        return Single.just(game)
    }

    override fun subscribeToGameUpdate(gameId: String): Observable<Game> {
        return Observable.empty()
    }

    override fun createGame(host: Player): Single<Game> {
        val game = Game("123", "Test", null, 0, IntRange(0, 8).associateBy({ it }, { "" }).toMutableMap())
        return Single.just(game)
    }

    override fun updateGame(game: Game) {
    }
}
