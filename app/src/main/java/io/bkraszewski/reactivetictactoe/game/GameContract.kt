package io.bkraszewski.reactivetictactoe.game

import io.bkraszewski.reactivetictactoe.base.BasePresenter
import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.GameState

interface GameContract {
    interface View {
        fun renderBoard(board: List<String>)
        fun showTurn(player: String)
        fun enableBoard(enabled: Boolean)

        companion object {
            val NULL = object : View {
                override fun setTurn(player: String) {
                }

                override fun setState(state: GameState) {
                }

                override fun renderBoard(board: List<String>) {
                }

                override fun showTurn(player: String) {
                }

                override fun enableBoard(enabled: Boolean) {
                }
            }
        }

        fun setState(state: GameState)
        fun setTurn(player: String)
    }

    interface Presenter : BasePresenter {
        fun onCreateGame()
        fun onJoinGame(game: Game)
    }
}
