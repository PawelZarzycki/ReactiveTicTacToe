package io.bkraszewski.reactivetictactoe.games_list

import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.service.DI
import io.bkraszewski.reactivetictactoe.service.GameService
import io.reactivex.disposables.CompositeDisposable

class ListPresenter(var view: ListContract.View, private val service: GameService = DI.gameService) : ListContract.Presenter {

    val disposables = CompositeDisposable()
    override fun onViewReady() {
        disposables.add(service.currentGames()
                .subscribe({ games ->
                    view.showGames(games)
                }, { throwable -> throwable.printStackTrace() })
        )
    }

    override fun detachView() {
        view = ListContract.View.NULL
        disposables.clear()
    }

    override fun onCreateGame() {
    }

    override fun onJoinGame(game: Game) {
    }

}
