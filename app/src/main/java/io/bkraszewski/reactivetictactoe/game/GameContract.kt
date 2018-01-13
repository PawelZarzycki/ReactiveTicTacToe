package io.bkraszewski.reactivetictactoe.game

import io.bkraszewski.reactivetictactoe.base.BasePresenter

interface GameContract {
    interface View {
        fun renderBoard(board: List<String>)
        fun showTurn(player: String)
        fun enableBoard(enabled: Boolean)

        companion object {
            val NULL = object : View {
                override fun renderBoard(board: List<String>) {
                }

                override fun showTurn(player: String) {
                }

                override fun enableBoard(enabled: Boolean) {
                }
            }
        }
    }

    interface Presenter : BasePresenter {
        fun onBoardChecked(index: Int)
    }
}
