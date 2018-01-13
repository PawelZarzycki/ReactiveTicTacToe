package io.bkraszewski.reactivetictactoe.game

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import io.bkraszewski.reactivetictactoe.R
import io.bkraszewski.reactivetictactoe.game.ui.BoardAdapter
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), GameContract.View {
    override fun renderBoard(board: List<String>) {
    }

    override fun showTurn(player: String) {
    }

    override fun enableBoard(enabled: Boolean) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val fields = IntRange(0, 8)
                .map { it -> it.toString() }
                .toList()
        gameBoard.adapter = BoardAdapter(fields, this)

        gameBoard.layoutManager = GridLayoutManager(this, 3)
        gameBoard.adapter.notifyDataSetChanged()
    }
}
