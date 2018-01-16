package io.bkraszewski.reactivetictactoe.game

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import io.bkraszewski.reactivetictactoe.R
import io.bkraszewski.reactivetictactoe.game.ui.BoardAdapter
import io.bkraszewski.reactivetictactoe.model.Game
import io.bkraszewski.reactivetictactoe.model.GameState
import io.bkraszewski.reactivetictactoe.service.DI
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : AppCompatActivity(), GameContract.View {
    override fun setTurn(player: String) {

    }

    override fun setState(state: GameState) {
        when (state) {
            GameState.WAITING_FOR_OPONENT -> gameStatus.text = "Waiting for Oponent"
            GameState.CONNECTING -> gameStatus.text = "Connecting..."
            GameState.GAME_RUNNING -> gameStatus.text = "Game Running"
        }
    }

    override fun renderBoard(board: List<String>) {
        adapter.update(board)
    }

    override fun showTurn(player: String) {
        actionBar?.title = "Player's turn: $player"
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

        val game = intent.getSerializableExtra(EXISTING_GAME) as Game?
        val mode = intent.getIntExtra(LAUNCH_MODE, MODE_CREATE)

        if(mode == MODE_CREATE) {
            presenter.onCreateGame()
        }else{
            presenter.onJoinGame(game!!)
        }
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

    companion object {
        val LAUNCH_MODE = "launchMode"
        val EXISTING_GAME = "existing_game"
        val MODE_CREATE = 0
        val MODE_JOIN = 1


        fun start(context: Context, mode: Int, game: Game? = null){
            val intent = Intent(context, GameActivity::class.java)
            intent.putExtra(LAUNCH_MODE, mode)
            intent.putExtra(EXISTING_GAME, game)
            context.startActivity(intent)
        }
    }
}
