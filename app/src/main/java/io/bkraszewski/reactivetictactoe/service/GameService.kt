package io.bkraszewski.reactivetictactoe.service

import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.Player
import io.reactivex.Observable
import io.reactivex.Single

interface GameService {
    fun createGame(host: Player): Single<Game>
    fun subscribeToGameUpdate(gameId: String): Observable<Game>
    fun joinGame(game: Game): Single<Game>
    fun updateGame(game: Game)
    fun currentGames(): Observable<List<Game>>
}
