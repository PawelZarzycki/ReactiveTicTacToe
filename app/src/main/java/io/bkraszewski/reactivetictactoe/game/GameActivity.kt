package io.bkraszewski.reactivetictactoe.game

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import io.bkraszewski.reactivetictactoe.R
import io.bkraszewski.reactivetictactoe.game.ui.BoardAdapter
import io.bkraszewski.reactivetictactoe.model.GameState
import io.bkraszewski.reactivetictactoe.service.DI
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), GameContract.View {
    override fun setState(state: GameState) {
        when (state) {
            GameState.WAITING_FOR_OPONENT -> gameStatus.text = "Waiting for Oponent"
        }
    }

    override fun renderBoard(board: List<String>) {
        adapter.update(board)
    }

    override fun showTurn(player: String) {
    }

    override fun enableBoard(enabled: Boolean) {

    }

    private lateinit var adapter: BoardAdapter

    private lateinit var presenter: GamePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        initializeBoard()

        presenter = GamePresenter(this, DI.gameService, DI.playerService)
        presenter.onViewReady()

        //assuming user creates game
        presenter.onCreateGame()
    }

    private fun initializeBoard() {
        val fields = IntRange(0, 8)
                .map { it -> it.toString() }
                .toList()
        adapter = BoardAdapter(fields, this)
        gameBoard.adapter = adapter
        gameBoard.layoutManager = GridLayoutManager(this, 3)
        gameBoard.adapter.notifyDataSetChanged()
    }
}
