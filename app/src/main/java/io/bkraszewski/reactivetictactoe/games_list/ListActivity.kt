package io.bkraszewski.reactivetictactoe.games_list

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import io.bkraszewski.reactivetictactoe.R
import io.bkraszewski.reactivetictactoe.model.Game
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.content_list.*

class ListActivity : AppCompatActivity(), ListContract.View {


    private lateinit var presenter: ListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        presenter = ListPresenter(this)
        presenter.onViewReady()

        fab.setOnClickListener { _ ->
            presenter.onCreateGame()
        }
    }

    override fun showGames(games: List<Game>) {
        val adapter = GamesListAdapter(games, this, { game -> presenter.onJoinGame(game) })

        gamesList.layoutManager = LinearLayoutManager(this)
        gamesList.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}
