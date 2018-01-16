package io.bkraszewski.reactivetictactoe.games_list

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.item_game.view.*

class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name:TextView = view.gameName
}
