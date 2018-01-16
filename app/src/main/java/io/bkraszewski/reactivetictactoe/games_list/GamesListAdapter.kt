package io.bkraszewski.reactivetictactoe.games_list

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import io.bkraszewski.reactivetictactoe.R
import io.bkraszewski.reactivetictactoe.model.Game

class GamesListAdapter(private var list: List<Game>, private var context: Context, val ļistener: (Game) -> Unit) : RecyclerView.Adapter<GameViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false)
        return GameViewHolder(view)
    }

    override fun onBindViewHolder(holder: GameViewHolder?, position: Int) {
        val game = list[position]
        holder?.name?.text = game.hostName
        holder?.itemView?.setOnClickListener { ļistener(game) }
    }

    override fun getItemCount(): Int {
        return list.size
    }

}
